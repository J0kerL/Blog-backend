package com.blog.service;

import com.blog.dto.CategoryDTO;
import com.blog.dto.CategoryPageQueryDTO;
import com.blog.entity.Category;
import com.blog.result.PageResult;

import java.util.List;

/**
 * @author Administrator
 */
public interface CategoryService {
    /**
     * 分类分页查询
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * 修改分类
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     */
    void deleteByIds(String ids);

    /**
     * 查询所有分类
     * @return
     */
    List<Category> list();
}