package com.blog.service;

import com.blog.result.PageResult;
import com.blog.dto.ArticleAddDTO;
import com.blog.dto.ArticleUpdateDTO;
import com.blog.dto.ArticlePageQueryDTO;
import com.blog.vo.ArticleVO;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:26
 */
public interface ArticleService {

    // 新增文章
    void add(ArticleAddDTO articleAddDTO);

    // 更新文章
    void update(ArticleUpdateDTO articleUpdateDTO);

    // 根据ID删除文章
    void deleteById(Integer id);

    // 批量删除文章
    void batchDelete(List<Integer> ids);

    // 获取文章详情
    ArticleVO getById(Integer id);

    // 文章分页查询
    PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO);
}
