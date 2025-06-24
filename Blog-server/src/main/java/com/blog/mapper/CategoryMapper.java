package com.blog.mapper;

import com.blog.dto.CategoryPageQueryDTO;
import com.blog.entity.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 分类分页查询
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据ID查询分类
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getById(Integer id);
    
    @Select("SELECT * FROM category WHERE name = #{name}")
    Category getByName(String name);

    /**
     * 新增分类
     */
    void addCategory(Category category);

    /**
     * 修改分类
     */
    void updateCategory(Category category);

    /**
     * 批量删除分类
     */
    void deleteByIds(List<Integer> ids);
}