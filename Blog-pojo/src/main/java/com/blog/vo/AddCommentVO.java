package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-08-08 11:14
 */
@Data
public class AddCommentVO {
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private String content;
    private Integer parentId;
    private LocalDateTime createTime;
}
