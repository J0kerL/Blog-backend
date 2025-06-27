package com.blog.service;

import java.util.Map;

/**
 * 仪表盘服务接口
 * @author Administrator
 */
public interface DashboardService {

    /**
     * 获取仪表盘统计数据
     * @return 包含文章数、用户数、评论数、浏览量的统计数据
     */
    Map<String, Object> getStats();

    /**
     * 获取最近七天访问统计
     * @return 最近七天的访问数据
     */
    Map<String, Object> getVisitStats();

    /**
     * 获取分类统计
     * @return 各分类的文章数量统计
     */
    Map<String, Object> getCategoryStats();

    /**
     * 获取文章贡献度数据
     * @return 一年内每天的文章发布数量
     */
    Map<String, Object> getContributionStats();
}