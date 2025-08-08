package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-08-08 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentDTO implements Serializable {

    // 评论ID
    private Integer id;

    // 文章ID
    private Integer articleId;

    // 评论用户ID
    private Integer userId;

    // 评论内容
    private String content;

    // 父评论ID（回复评论时使用）
    private Integer parentId;
    
    // 被回复的评论ID（用于显示回复关系）
    private Integer replyToCommentId;

    // 创建时间
    private LocalDateTime createTime;
}
