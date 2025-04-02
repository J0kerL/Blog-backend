package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Java小猪
 * @Date 2025/4/2 17:08
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射路径
        registry.addMapping("/**")
                // 允许哪些域的请求，星号代表允许所有
                .allowedOrigins("*")
                // 允许的方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 允许的头部设置
                .allowedHeaders("*")
                // 是否发送cookie
                .allowCredentials(true)
                // 预检间隔时间
                .maxAge(168000);
    }
}
