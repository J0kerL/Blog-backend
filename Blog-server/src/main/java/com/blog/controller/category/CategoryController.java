package com.blog.controller.category;

import com.blog.dto.*;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/category")
@Slf4j
@Tag(name = "分类相关接口")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 分类分页查询
     */
    @Operation(summary = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增分类
     */
    @PostMapping("/add")
    @Operation(summary = "新增分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        return Result.success("新增分类成功");
    }

    /**
     * 修改分类
     */
    @PutMapping("/update")
    @Operation(summary = "修改分类")
    public Result<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success("修改分类成功");
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除分类")
    public Result<String> deleteByIds(@RequestParam("ids") String ids) {
        categoryService.deleteByIds(ids);
        return Result.success("删除分类成功");
    }
}