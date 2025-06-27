package com.blog.service.impl;

import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.DashboardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public Map<String, Object> getVisitStats() {
        Map<String, Object> visitStats = new HashMap<>();
        
        // 获取最近7天的日期
        List<String> dates = new ArrayList<>();
        List<Integer> visits = new ArrayList<>();
        
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            dates.add(date.format(formatter));
            
            // 生成随机访问数据，每次刷新页面都会变化
            visits.add(100 + (int)(Math.random() * 200));
        }
        
        visitStats.put("dates", dates);
        visitStats.put("visits", visits);
        
        return visitStats;
    }

    @Override
    public Map<String, Object> getCategoryStats() {
        Map<String, Object> categoryStats = new HashMap<>();
        
        try {
            // 获取各分类的文章数量
            List<Map<String, Object>> categoryData = categoryMapper.getCategoryArticleCount();
            
            List<String> names = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            List<String> colors = Arrays.asList("#409EFF", "#67C23A", "#E6A23C", "#F56C6C", "#909399");
            
            for (int i = 0; i < categoryData.size() && i < 5; i++) {
                Map<String, Object> item = categoryData.get(i);
                names.add((String) item.get("name"));
                values.add(((Number) item.get("count")).intValue());
            }
            
            categoryStats.put("names", names);
            categoryStats.put("values", values);
            categoryStats.put("colors", colors.subList(0, Math.min(names.size(), colors.size())));
            
        } catch (Exception e) {
            log.error("获取分类统计失败", e);
            // 返回默认数据
            categoryStats.put("names", Arrays.asList("技术文章", "生活随笔", "学习笔记"));
            categoryStats.put("values", Arrays.asList(35, 25, 20));
            categoryStats.put("colors", Arrays.asList("#409EFF", "#67C23A", "#E6A23C"));
        }
        
        return categoryStats;
    }

    @Override
    public Map<String, Object> getContributionStats() {
        Map<String, Object> contributionStats = new HashMap<>();
        
        // 生成今年的文章贡献度数据（均匀分布的多中少）
        Map<String, Integer> contributionMap = new HashMap<>();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.now();
        
        // 创建一个随机但固定的种子，确保数据一致性
        Random random = new Random(2024);
        
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            // 生成0-4的文章数量，实现多中少的均匀分布
            // 0: 40%, 1: 25%, 2: 20%, 3: 10%, 4: 5%
            int rand = random.nextInt(100);
            int count;
            if (rand < 40) {
                count = 0;  // 无文章
            } else if (rand < 65) {
                count = 1;  // 少量文章
            } else if (rand < 85) {
                count = 2;  // 中等文章
            } else if (rand < 95) {
                count = 3;  // 较多文章
            } else {
                count = 4;  // 大量文章
            }
            
            contributionMap.put(currentDate.toString(), count);
            currentDate = currentDate.plusDays(1);
        }
        
        contributionStats.put("data", contributionMap);
        contributionStats.put("year", LocalDate.now().getYear());
        
        return contributionStats;
    }
}