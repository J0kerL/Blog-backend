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
     * 获取分类统计数据
     * @return 包含各分类文章数量的统计数据
     */
    Map<String, Object> getCategoryStats();
}