package com.blog.service;

import com.blog.dto.AddCommentDTO;
import com.blog.dto.CommentAdminPageQueryDTO;
import com.blog.dto.CommentPageQueryDTO;
import com.blog.result.PageResult;
import com.blog.vo.AddCommentVO;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-01-27
 */
public interface CommentService {
    
    /**
     * 分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO);
    
    /**
     * 批量删除评论
     * @param ids
     */
    void batchDelete(List<Integer> ids);

    /**
     * 添加评论
     * @param addCommentDTO
     * @return
     */
    AddCommentVO createComment(AddCommentDTO addCommentDTO);

    /**
     * 根据文章ID获取总评论数量（包括子评论）
     * @param articleId
     * @return
     */
    Long getTotalCommentCount(Integer articleId);

    /**
     * 管理端分页查询评论
     * @param commentAdminPageQueryDTO
     * @return
     */
    PageResult adminPageQuery(CommentAdminPageQueryDTO commentAdminPageQueryDTO);
}