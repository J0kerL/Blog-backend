package com.blog.controller.article;

import com.blog.result.PageResult;
import com.blog.dto.ArticleAddDTO;
import com.blog.dto.ArticleUpdateDTO;
import com.blog.dto.ArticlePageQueryDTO;
import com.blog.result.Result;
import com.blog.service.ArticleService;
import com.blog.vo.ArticleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:25
 */
@RestController
@RequestMapping("/article")
@Tag(name = "文章管理接口")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    @Operation(summary = "新增文章")
    public Result<Void> add(@RequestBody ArticleAddDTO articleAddDTO) {
        articleService.add(articleAddDTO);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "更新文章")
    public Result<Void> update(@RequestBody ArticleUpdateDTO articleUpdateDTO) {
        articleService.update(articleUpdateDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除文章")
    public Result<Void> deleteById(@PathVariable Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除文章")
    public Result<Void> batchDelete(@RequestBody List<Integer> ids) {
        articleService.batchDelete(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章详情")
    public Result<ArticleVO> getById(@PathVariable Integer id) {
        ArticleVO articleVO = articleService.getById(id);
        return Result.success(articleVO);
    }

    @GetMapping("/page")
    @Operation(summary = "文章分页查询")
    public Result<PageResult> pageQuery(ArticlePageQueryDTO articlePageQueryDTO) {
        PageResult pageResult = articleService.pageQuery(articlePageQueryDTO);
        return Result.success(pageResult);
    }
}
