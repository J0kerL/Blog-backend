package com.blog.controller.file;

import com.blog.annotation.RequireLogin;
import com.blog.result.Result;
import com.blog.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 文件上传控制器
 * @author Administrator
 */
@RestController
@RequestMapping("/file")
@Slf4j
@Tag(name = "文件上传相关接口")
public class FileController {

    @Resource
    private AliOssUtil aliOssUtil;

    // 支持的图片文件类型
    private static final Set<String> ALLOWED_IMAGE_TYPES = new HashSet<>(Arrays.asList(
            ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp", ".svg"
    ));

    // 支持的文档文件类型
    private static final Set<String> ALLOWED_DOCUMENT_TYPES = new HashSet<>(Arrays.asList(
            ".doc", ".docx", ".pdf", ".txt", ".zip", ".rar", ".xls", ".xlsx", ".ppt", ".pptx"
    ));

    // 支持的视频文件类型
    private static final Set<String> ALLOWED_VIDEO_TYPES = new HashSet<>(Arrays.asList(
            ".mp4", ".avi", ".mov", ".wmv", ".flv", ".webm", ".mkv"
    ));

    // 支持的音频文件类型
    private static final Set<String> ALLOWED_AUDIO_TYPES = new HashSet<>(Arrays.asList(
            ".mp3", ".wav", ".flac", ".aac", ".ogg", ".m4a"
    ));

    // 不同类型文件的大小限制
    // 图片最大5MB
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;
    // 文档最大20MB
    private static final long MAX_DOCUMENT_SIZE = 20 * 1024 * 1024;
    // 视频最大100MB
    private static final long MAX_VIDEO_SIZE = 100 * 1024 * 1024;
    // 音频最大10MB
    private static final long MAX_AUDIO_SIZE = 10 * 1024 * 1024;

    // 文件名安全性检查正则表达式
    private static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._\\u4e00-\\u9fa5-]+$");
    
    // 日期格式化器，用于创建按日期分类的目录结构
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * 统一文件上传接口
     * @param file 上传的文件
     * @param type 文件类型（avatar-头像，image-图片，document-文档，video-视频，audio-音频）
     * @return 文件访问URL
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传", description = "支持图片、文档、视频、音频等多种文件类型上传到阿里云OSS")
    @RequireLogin // 需要登录
    public Result<String> uploadFile(
            @RequestParam("file") @Parameter(description = "要上传的文件") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "image") @Parameter(description = "文件类型：avatar/image/document/video/audio") String type) {
        
        try {
            // 文件基础验证
            Result<String> validationResult = validateFile(file, type);
            if (!Integer.valueOf(200).equals(validationResult.getCode())) {
                return validationResult;
            }

            // 获取文件信息
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            
            // 构建OSS存储路径（按日期分类）
            String objectName = buildObjectName(type, extension);

            // 上传到阿里云OSS
            String url = aliOssUtil.upload(file.getBytes(), objectName);
            
            log.info("文件上传成功 - 类型: {}, 大小: {}KB, 存储路径: {}, 访问URL: {}", 
                    type, file.getSize() / 1024, objectName, url);
            return Result.success(url);
            
        } catch (IOException e) {
            log.error("文件上传失败 - 文件名: {}, 错误信息: ", file.getOriginalFilename(), e);
            return Result.error("文件上传失败，请重试");
        } catch (Exception e) {
            log.error("文件上传异常 - 文件名: {}, 异常信息: ", file.getOriginalFilename(), e);
            return Result.error("系统异常，请联系管理员");
        }
    }

    /**
     * 批量文件上传接口
     * @param files 文件数组
     * @param type 文件类型
     * @return 上传成功的文件URL列表
     */
    @PostMapping("/batch-upload")
    @Operation(summary = "批量文件上传", description = "一次性上传多个文件，最多支持10个文件")
    @RequireLogin // 需要登录
    public Result<List<String>> batchUploadFiles(
            @RequestParam("files") @Parameter(description = "要上传的文件列表") MultipartFile[] files,
            @RequestParam(value = "type", defaultValue = "image") @Parameter(description = "文件类型") String type) {
        
        // 检查文件数组
        if (files == null || files.length == 0) {
            return Result.error("请选择要上传的文件");
        }
        
        if (files.length > 10) {
            return Result.error("单次最多上传10个文件");
        }

        List<String> successUrls = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        // 逐个上传文件
        for (MultipartFile file : files) {
            try {
                Result<String> result = uploadFile(file, type);
                if (result.getCode().equals(200)) {
                    successUrls.add(result.getData());
                } else {
                    failedFiles.add(file.getOriginalFilename() + ": " + result.getMsg());
                }
            } catch (Exception e) {
                failedFiles.add(file.getOriginalFilename() + ": 上传异常");
                log.error("批量上传文件失败: {}", file.getOriginalFilename(), e);
            }
        }

        // 返回结果
        if (!failedFiles.isEmpty()) {
            log.warn("批量上传部分文件失败: {}", failedFiles);
            return Result.error("部分文件上传失败: " + String.join("; ", failedFiles));
        }

        log.info("批量上传成功，共上传{}个文件", successUrls.size());
        return Result.success(successUrls);
    }

    /**
     * 文件验证方法
     * @param file 上传的文件
     * @param type 文件类型
     * @return 验证结果
     */
    private Result<String> validateFile(MultipartFile file, String type) {
        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            return Result.error("请选择要上传的文件");
        }

        // 检查文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            return Result.error("文件名不能为空");
        }

        // 获取文件扩展名
        String extension = getFileExtension(originalFilename);
        if (extension.isEmpty()) {
            return Result.error("文件必须有扩展名");
        }

        // 检查文件类型是否支持
        if (!isValidFileType(extension, type)) {
            return Result.error("不支持的文件类型: " + extension + "，请上传" + getSupportedTypesDesc(type));
        }

        // 检查文件大小
        long maxSize = getMaxFileSizeByType(type);
        if (file.getSize() > maxSize) {
            return Result.error("文件大小超过限制，最大允许: " + (maxSize / 1024 / 1024) + "MB");
        }

        // 检查文件名安全性
        if (!isFilenameSafe(originalFilename)) {
            return Result.error("文件名包含非法字符，请使用字母、数字、中文、点号、下划线或短横线");
        }

        return Result.success("验证通过");
    }

    /**
     * 获取文件扩展名
     * @param filename 文件名
     * @return 扩展名（包含点号）
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 检查文件类型是否有效
     * @param extension 文件扩展名
     * @param type 文件类型
     * @return 是否有效
     */
    private boolean isValidFileType(String extension, String type) {
        switch (type.toLowerCase()) {
            case "avatar":
            case "image":
                return ALLOWED_IMAGE_TYPES.contains(extension);
            case "document":
                return ALLOWED_DOCUMENT_TYPES.contains(extension);
            case "video":
                return ALLOWED_VIDEO_TYPES.contains(extension);
            case "audio":
                return ALLOWED_AUDIO_TYPES.contains(extension);
            default:
                return false;
        }
    }

    /**
     * 根据文件类型获取大小限制
     * @param type 文件类型
     * @return 大小限制（字节）
     */
    private long getMaxFileSizeByType(String type) {
        switch (type.toLowerCase()) {
            case "avatar":
            case "image":
                return MAX_IMAGE_SIZE;
            case "document":
                return MAX_DOCUMENT_SIZE;
            case "video":
                return MAX_VIDEO_SIZE;
            case "audio":
                return MAX_AUDIO_SIZE;
            default:
                return MAX_IMAGE_SIZE;
        }
    }

    /**
     * 获取支持的文件类型描述
     * @param type 文件类型
     * @return 类型描述
     */
    private String getSupportedTypesDesc(String type) {
        switch (type.toLowerCase()) {
            case "avatar":
            case "image":
                return "图片文件(jpg, jpeg, png, gif, bmp, webp, svg)";
            case "document":
                return "文档文件(doc, docx, pdf, txt, zip, rar, xls, xlsx, ppt, pptx)";
            case "video":
                return "视频文件(mp4, avi, mov, wmv, flv, webm, mkv)";
            case "audio":
                return "音频文件(mp3, wav, flac, aac, ogg, m4a)";
            default:
                return "支持的文件类型";
        }
    }

    /**
     * 检查文件名安全性
     * @param filename 文件名
     * @return 是否安全
     */
    private boolean isFilenameSafe(String filename) {
        // 移除路径部分，只检查文件名
        String nameOnly = filename.substring(Math.max(filename.lastIndexOf("/"), filename.lastIndexOf("\\")) + 1);
        
        // 检查文件名长度
        if (nameOnly.length() > 255) {
            return false;
        }
        
        // 检查是否包含危险字符或路径遍历
        return !nameOnly.contains("..") && 
               !nameOnly.contains("/") && 
               !nameOnly.contains("\\") &&
               !nameOnly.contains("<") &&
               !nameOnly.contains(">") &&
               !nameOnly.contains("|") &&
               !nameOnly.contains("*") &&
               !nameOnly.contains("?") &&
               !nameOnly.contains("\"");
    }

    /**
     * 构建OSS对象存储路径
     * @param type 文件类型
     * @param extension 文件扩展名
     * @return 完整的存储路径
     */
    private String buildObjectName(String type, String extension) {
        String directory = getDirectoryByType(type);
        String filename = UUID.randomUUID().toString().replace("-", "");
        
        return String.format("%s/%s%s", directory, filename, extension);
    }

    /**
     * 根据类型获取存储目录
     * @param type 文件类型
     * @return 存储目录名
     */
    private String getDirectoryByType(String type) {
        switch (type.toLowerCase()) {
            case "avatar":
                return "avatar";
            case "image":
                return "images";
            case "document":
                return "documents";
            case "video":
                return "videos";
            case "audio":
                return "audios";
            default:
                return "others";
        }
    }
} 