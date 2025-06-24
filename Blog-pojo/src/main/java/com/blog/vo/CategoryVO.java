package com.blog.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryVO {
    private Integer id;
    private String name;
    private String description;
    private Integer articleCount;
}