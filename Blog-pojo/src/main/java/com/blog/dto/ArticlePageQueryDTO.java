package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 15:08
 */
@Data
@Schema(description = "文章分页查询")
public class ArticlePageQueryDTO implements Serializable {
    // 文章标题
    private String title;

    // 分类ID
    private Integer categoryId;

    // 单个标签ID
    private Integer tagId;

    // 标签ID列表
    private List<Integer> tagIds;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;

    // 当前用户ID（用于过滤草稿文章）
    private Integer currentUserId;
}
