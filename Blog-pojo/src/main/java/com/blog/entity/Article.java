package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Diamond
 * @create 2025-06-04 14:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Integer categoryId;
    private Integer authorId;
    // 0-草稿，1-已发布
    private Integer status;
}
