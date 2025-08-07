package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:23
 */
@Data
@Schema(description = "新增文章")
public class ArticleAddDTO implements Serializable {
    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "文章封面")
    private String cover;

    @Schema(description = "分类ID")
    private Integer categoryId;

    @Schema(description = "状态：0-草稿，1-已发布")
    private Integer status;

    @Schema(description = "标签ID列表")
    private List<Integer> tagIds;
} 