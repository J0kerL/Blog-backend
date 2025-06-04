package com.blog.mapper;

import com.blog.dto.ArticlePageQueryDTO;
import com.blog.entity.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:26
 */
public interface ArticleMapper {
    /**
     * 新增文章
     * @param article
     * @return
     */
    int insert(Article article);

    /**
     * 更新文章
     * @param article
     * @return
     */
    int update(Article article);

    /**
     * 根据ID删除文章
     * @param id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    int batchDelete(@Param("ids") List<Integer> ids);

    /**
     * 根据ID查询文章
     * @param id
     * @return
     */
    Article selectById(@Param("id") Integer id);

    /**
     * 文章分页查询
     * @param articlePageQueryDTO
     * @return
     */
    Page<Article> pageQuery(ArticlePageQueryDTO articlePageQueryDTO);
}
