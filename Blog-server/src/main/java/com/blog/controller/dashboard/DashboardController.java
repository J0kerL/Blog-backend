package com.blog.controller.dashboard;

import com.blog.result.Result;
import com.blog.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 仪表盘控制器
 * @author Administrator
 */
@RestController
@RequestMapping("/dashboard")
@Tag(name = "仪表盘相关接口")
@Slf4j
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    /**
     * 获取仪表盘统计数据
     * @return
     */
    @GetMapping("/stats")
    @Operation(summary = "获取仪表盘统计数据")
    public Result<Map<String, Object>> getStats() {
        log.info("获取仪表盘统计数据");
        Map<String, Object> stats = dashboardService.getStats();
        return Result.success(stats);
    }

    /**
     * 获取分类统计数据
     * @return
     */
    @GetMapping("/categories")
    @Operation(summary = "获取分类统计数据")
    public Result<Map<String, Object>> getCategoryStats() {
        log.info("获取分类统计数据");
        Map<String, Object> categoryStats = dashboardService.getCategoryStats();
        return Result.success(categoryStats);
    }
}