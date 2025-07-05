package com.blog.service.impl;

import com.blog.dto.CategoryStatsDTO;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.DashboardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 仪表盘服务实现类
 * @author Administrator
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // 获取文章总数
            Long articleCount = articleMapper.countArticles();
            stats.put("articleCount", articleCount != null ? articleCount : 0);
            
            // 获取用户总数
            Long userCount = userMapper.countUsers();
            stats.put("userCount", userCount != null ? userCount : 0);
            
            // 获取评论总数
            Long commentCount = commentMapper.countComments();
            stats.put("commentCount", commentCount != null ? commentCount : 0);
            
            // 获取总浏览量（固定值）
            stats.put("viewCount", 20668);
            
        } catch (Exception e) {
            log.error("获取统计数据失败", e);
            // 返回默认值
            stats.put("articleCount", 0);
            stats.put("userCount", 0);
            stats.put("commentCount", 0);
            stats.put("viewCount", 20668);
        }
        
        return stats;
    }

    @Override
    public Map<String, Object> getCategoryStats() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取分类统计数据
            List<CategoryStatsDTO> categoryStatsList = categoryMapper.getCategoryStats();
            
            // 转换为前端需要的格式
            List<Map<String, Object>> data = categoryStatsList.stream()
                .map(stats -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("name", stats.getName());
                    item.put("value", stats.getCount());
                    return item;
                })
                .collect(Collectors.toList());
            
            result.put("data", data);
            
        } catch (Exception e) {
            log.error("获取分类统计数据失败", e);
            // 返回默认数据
            List<Map<String, Object>> defaultData = new ArrayList<>();
            Map<String, Object> defaultItem = new HashMap<>();
            defaultItem.put("name", "默认分类");
            defaultItem.put("value", 0);
            defaultData.add(defaultItem);
            result.put("data", defaultData);
        }
        
        return result;
    }
}