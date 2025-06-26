package com.blog.service;

import com.blog.dto.CommentPageQueryDTO;
import com.blog.result.PageResult;

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
}