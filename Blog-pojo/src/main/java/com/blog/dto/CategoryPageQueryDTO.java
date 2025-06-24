package com.blog.dto;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class CategoryPageQueryDTO {
    private Integer page;
    private Integer pageSize;
    private String name;
}