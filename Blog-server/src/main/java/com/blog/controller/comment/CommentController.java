package com.blog.controller.comment;

import com.blog.annotation.RequireLogin;
import com.blog.dto.AddCommentDTO;
import com.blog.dto.CommentAdminPageQueryDTO;
import com.blog.dto.CommentPageQueryDTO;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.CommentService;
import com.blog.vo.AddCommentVO;
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
     * 管理端分页查询评论
     */
    @GetMapping("/admin/page")
    @Operation(summary = "管理端分页查询评论")
    @RequireLogin // 需要登录
    public Result<PageResult> pageAdminQuery(CommentAdminPageQueryDTO commentAdminPageQueryDTO){
        log.info("管理端分页查询评论，参数：{}", commentAdminPageQueryDTO);
        PageResult pageResult = commentService.adminPageQuery(commentAdminPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 用户端分页查询评论
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
     * 添加评论
     */
    @Operation(summary = "添加评论")
    @PostMapping("/add")
    @RequireLogin
    public Result<AddCommentVO> createComment(@RequestBody AddCommentDTO addCommentDTO){
        log.info("添加评论：{}", addCommentDTO);
        AddCommentVO addCommentVO = commentService.createComment(addCommentDTO);
        return Result.success(addCommentVO);
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

    /**
     * 获取文章总评论数量（包括子评论）
     */
    @Operation(summary = "获取文章总评论数量")
    @GetMapping("/count/{articleId}")
    @RequireLogin(false) // 公开接口，不需要登录
    public Result<Long> getTotalCommentCount(@PathVariable Integer articleId) {
        log.info("获取文章{}的总评论数量", articleId);
        Long count = commentService.getTotalCommentCount(articleId);
        return Result.success(count);
    }
}