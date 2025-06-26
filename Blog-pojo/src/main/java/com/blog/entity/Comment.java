package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-01-27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    
    private Integer id;
    
    // 文章ID
    private Integer articleId;
    
    // 评论用户ID
    private Integer userId;
    
    // 评论内容
    private String content;
    
    // 父评论ID（回复评论时使用）
    private Integer parentId;
    
    // 创建时间
    private LocalDateTime createTime;
    
    // 更新时间
    private LocalDateTime updateTime;
}