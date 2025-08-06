package com.blog.controller.comment;

import com.blog.annotation.RequireLogin;
import com.blog.dto.CommentPageQueryDTO;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-01-27
 */
@RestController
@RequestMapping("/comment")
@Slf4j
@Tag(name = "评论管理接口")
public class CommentController {
    
    @Resource
    private CommentService commentService;
    
    /**
     * 分页查询评论
     */
    @Operation(summary = "评论分页查询")
    @GetMapping("/page")
    @RequireLogin(false) // 公开接口，不需要登录
    public Result<PageResult> pageQuery(CommentPageQueryDTO commentPageQueryDTO) {
        log.info("评论分页查询，参数：{}", commentPageQueryDTO);
        PageResult pageResult = commentService.pageQuery(commentPageQueryDTO);
        return Result.success(pageResult);
    }
    
    /**
     * 批量删除评论
     */
    @Operation(summary = "批量删除评论")
    @DeleteMapping("/batch")
    @RequireLogin // 需要登录
    public Result<String> batchDelete(@RequestBody List<Integer> ids) {
        log.info("批量删除评论，ids：{}", ids);
        commentService.batchDelete(ids);
        return Result.success("删除成功");
    }
}