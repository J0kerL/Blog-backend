package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类统计数据传输对象
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStatsDTO {
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 该分类下的文章数量
     */
    private Long count;
}