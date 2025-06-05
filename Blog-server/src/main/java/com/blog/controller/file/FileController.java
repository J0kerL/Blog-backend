package com.blog.controller.file;

import com.blog.constant.Constant;
import com.blog.result.Result;
import com.blog.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    // 允许的文件类型
    private static final Set<String> ALLOWED_IMAGE_TYPES = new HashSet<>(Arrays.asList(
            ".jpg", ".jpeg", ".png", ".gif", ".bmp"
    ));

    private static final Set<String> ALLOWED_DOCUMENT_TYPES = new HashSet<>(Arrays.asList(
            ".doc", ".docx", ".pdf", ".txt", ".zip", ".rar"
    ));

    // 文件大小限制（10MB）
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 统一文件上传接口
     * @param file 文件
     * @param type 文件类型（avatar-头像，image-图片，document-文档）
     * @return 文件访问URL
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "image") String type) {
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                return Result.error("文件名不能为空");
            }

            // 获取文件后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            // 文件类型检查
            if (!isValidFileType(extension, type)) {
                return Result.error("不支持的文件类型");
            }

            // 文件大小检查
            if (file.getSize() > MAX_FILE_SIZE) {
                return Result.error("文件大小不能超过10MB");
            }

            // 根据类型确定存储目录
            String directory = getDirectoryByType(type);

            // 构造新文件名
            String objectName = directory + "/" + UUID.randomUUID().toString() + extension;

            // 上传文件
            String url = aliOssUtil.upload(file.getBytes(), objectName);
            log.info("文件上传成功：{}", url);
            return Result.success(url);
        } catch (IOException e) {
            log.error("文件上传失败：", e);
            return Result.error(Constant.FILE_UPLOAD_FAIL);
        }
    }

    /**
     * 检查文件类型是否有效
     */
    private boolean isValidFileType(String extension, String type) {
        switch (type.toLowerCase()) {
            case "avatar":
            case "image":
                return ALLOWED_IMAGE_TYPES.contains(extension);
            case "document":
                return ALLOWED_DOCUMENT_TYPES.contains(extension);
            default:
                return false;
        }
    }

    /**
     * 根据类型获取存储目录
     */
    private String getDirectoryByType(String type) {
        switch (type.toLowerCase()) {
            case "avatar":
                return "avatar";
            case "image":
                return "image";
            case "document":
                return "document";
            default:
                return "other";
        }
    }
} 