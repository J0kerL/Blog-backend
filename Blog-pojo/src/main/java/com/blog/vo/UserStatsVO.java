package com.blog.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 用户个人统计数据VO
 * @Author Diamond
 * @Date 2025/8/5
 */
@Data
@Builder
public class UserStatsVO {

    /**
     * 用户发表的文章数量
     */
    private Long articleCount;

    /**
     * 用户发表的评论数量
     */
    private Long commentCount;

}