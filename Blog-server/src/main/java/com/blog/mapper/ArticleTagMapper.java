package com.blog.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:30
 */
public interface ArticleTagMapper {

    /**
     * 根据文章ID查询标签名称列表
     * @param articleId
     * @return
     */
    List<String> selectTagNamesByArticleId(@Param("articleId") Integer articleId);

    /**
     * 批量插入文章-标签关联
     * @param articleId
     * @param tagIds
     * @return
     */
    int batchInsert(@Param("articleId") Integer articleId, @Param("tagIds") List<Integer> tagIds);

    /**
     * 根据标签ID统计文章数量
     * @param tagId
     * @return
     */
    int countByTagId(@Param("tagId") Integer tagId);

    /**
     * 根据文章ID删除文章-标签关联
     * @param articleId
     * @return
     */
    int deleteByArticleId(@Param("articleId") Integer articleId);

    /**
     * 批量删除文章-标签关联
     * @param articleIds
     * @return
     */
    int batchDeleteByArticleIds(@Param("articleIds") List<Integer> articleIds);
}
