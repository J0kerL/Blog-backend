package com.blog.mapper;

import com.blog.dto.CategoryPageQueryDTO;
import com.blog.entity.Category;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询所有分类
     * @return
     */
    List<Category> list();

    /**
     * 获取各分类的文章数量统计
     * @return
     */
    @Select("SELECT c.name, COUNT(a.id) as count FROM category c LEFT JOIN article a ON c.id = a.category_id GROUP BY c.id, c.name ORDER BY count DESC")
    List<Map<String, Object>> getCategoryArticleCount();
}