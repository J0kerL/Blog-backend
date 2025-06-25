package com.blog.controller.tag;

import com.blog.dto.TagDTO;
import com.blog.entity.Tag;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.blog.constant.Constant.OPERATE_SUCCESS;

/**
 * @Author Java小猪
 * @Date 2025/4/3 17:00
 */
@io.swagger.v3.oas.annotations.tags.Tag(name = "标签管理接口")
@RestController
@RequestMapping("/admin/tag")
@Slf4j
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 分页查询标签
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询标签")
    public Result<PageResult> page(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   String name) {
        log.info("分页查询标签，参数：{},{},{}", page, pageSize, name);
        PageResult pageResult = tagService.page(page, pageSize, name);
        return Result.success(pageResult);
    }

    /**
     * 新增标签
     * @param tagDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增标签")
    public Result<String> addTag(@RequestBody @Valid TagDTO tagDTO) {
        log.info("新增标签：{}", tagDTO);
        tagService.addTag(tagDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 修改标签
     * @param tagDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改标签")
    public Result<String> updateTag(@RequestBody @Valid TagDTO tagDTO) {
        log.info("修改标签：{}", tagDTO);
        tagService.updateTag(tagDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 批量删除标签
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary = "批量删除标签")
    public Result<String> deleteByIds(String ids) {
        log.info("批量删除标签：{}", ids);
        tagService.deleteByIds(ids);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有标签")
    public Result<List<Tag>> list() {
        log.info("查询所有标签");
        List<Tag> tags = tagService.list();
        return Result.success(tags);
    }

}