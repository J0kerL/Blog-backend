package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Diamond
 * @create 2025-06-04 15:34
 */
@Data
@Schema(description = "分类分页查询")
public class CategoryPageQueryDTO implements Serializable {
    // 分类名称
    private String name;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;
}
