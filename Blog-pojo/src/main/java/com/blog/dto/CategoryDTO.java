package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Diamond
 * @create 2025-06-04 15:33
 */
@Data
@Schema(description = "分类")
public class CategoryDTO implements Serializable {
    @Schema(description = "分类ID")
    private Integer id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "分类描述")
    private String description;
}
