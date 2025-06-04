package com.blog.mapper;

import com.blog.entity.Category;
import org.apache.ibatis.annotations.Param;

/**
 * @author Diamond
 * @create 2025-06-04 14:30
 */
public interface CategoryMapper {

    /**
     * 根据ID查询分类
     * @param id
     * @return
     */
    Category selectById(@Param("id") Integer id);

}
