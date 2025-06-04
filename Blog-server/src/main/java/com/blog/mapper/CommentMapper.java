package com.blog.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:54
 */
public interface CommentMapper {

    /**
     * 根据文章ID删除评论
     * @param articleId
     * @return
     */
    int deleteByArticleId(@Param("articleId") Integer articleId);

    /**
     * 批量删除文章评论
     * @param articleIds
     * @return
     */
    int batchDeleteByArticleIds(@Param("articleIds") List<Integer> articleIds);

}
