package com.blog.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {
    private Integer id;
    private String title;
    private String content;
    private Integer categoryId;
    private String categoryName;
    private Integer authorId;
    private String authorName;
    private Integer status;
    private List<String> tags;
}
