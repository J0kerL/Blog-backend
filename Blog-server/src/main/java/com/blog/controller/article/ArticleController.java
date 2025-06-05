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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.blog.constant.Constant.OPERATE_SUCCESS;

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

    @PostMapping("/add")
    @Operation(summary = "新增文章")
    public Result<String> add(@RequestBody ArticleAddDTO articleAddDTO) {
        articleService.add(articleAddDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    @PutMapping("/update")
    @Operation(summary = "更新文章")
    public Result<String> update(@RequestBody ArticleUpdateDTO articleUpdateDTO) {
        articleService.update(articleUpdateDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /*@DeleteMapping("/del/{id}")
    @Operation(summary = "根据ID删除文章")
    public Result<String> deleteById(@PathVariable Integer id) {
        articleService.deleteById(id);
        return Result.success(OPERATE_SUCCESS);
    }*/

    @DeleteMapping("/delete")
    @Operation(summary = "批量删除文章")
    public Result<String> batchDelete(@RequestParam("ids") String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        articleService.batchDelete(idList);
        return Result.success(OPERATE_SUCCESS);
    }

    @GetMapping("/get/{id}")
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
