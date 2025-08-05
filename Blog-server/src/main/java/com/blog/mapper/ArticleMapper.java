package com.blog.mapper;

import com.blog.dto.ArticlePageQueryDTO;
import com.blog.entity.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Diamond
 * @create 2025-06-04 14:26
 */
public interface ArticleMapper {
    /**
     * 新增文章
     *
     * @param article
     * @return
     */
    int insert(Article article);

    /**
     * 更新文章
     *
     * @param article
     * @return
     */
    int update(Article article);

    /**
     * 根据ID删除文章
     *
     * @param id
     * @return
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 批量删除文章
     *
     * @param ids
     * @return
     */
    int batchDelete(@Param("ids") List<Integer> ids);

    /**
     * 根据ID查询文章
     *
     * @param id
     * @return
     */
    Article selectById(@Param("id") Integer id);

    /**
     * 文章分页查询
     *
     * @param articlePageQueryDTO
     * @return
     */
    Page<Article> pageQuery(ArticlePageQueryDTO articlePageQueryDTO);

    /**
     * 根据分类ID统计文章数量
     *
     * @param categoryId
     * @return
     */
    int countByCategoryId(@Param("categoryId") Integer categoryId);

    /**
     * 统计文章总数
     *
     * @return
     */
    Long countArticles();

    /**
     * 统计总浏览量
     *
     * @return
     */
    Long sumViewCount();

    /**
     * 获取每日文章发布数量
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getDailyArticleCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 根据作者ID统计文章数量
     *
     * @param authorId
     * @return
     */
    Long countByAuthorId(@Param("authorId") Integer authorId);
}
