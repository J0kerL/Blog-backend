package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Diamond
 * @create 2025-06-04 15:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    private Integer id;
    private String name;
    private String description;
}
