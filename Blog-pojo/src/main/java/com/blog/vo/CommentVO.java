package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Diamond
 * @create 2025-01-27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    
    private Integer id;
    
    // 文章ID
    private Integer articleId;
    
    // 文章标题
    private String articleTitle;
    
    // 评论用户ID
    private Integer userId;
    
    // 评论用户名
    private String username;
    
    // 评论用户头像
    private String userAvatar;
    
    // 评论内容
    private String content;
    
    // 父评论ID
    private Integer parentId;
    
    // 创建时间
    private LocalDateTime createTime;

}