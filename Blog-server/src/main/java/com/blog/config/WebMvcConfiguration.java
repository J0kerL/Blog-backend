package com.blog.config;

import com.blog.interceptor.JwtTokenInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author Java小猪
 * @Date 2025/3/26 10:15
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Resource
    private JwtTokenInterceptor jwtTokenInterceptor;

    /**
     * 注册自定义拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/admin/**", "/user/**")
                .excludePathPatterns("/**/login/**", "/**/register/**");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                // 配置接口文档基本信息
                .info(this.getApiInfo());
    }

    private Info getApiInfo() {
        return new Info()
                // 配置文档标题
                .title("个人博客项目接口文档")
                // 配置文档描述
                .description("个人博客项目接口文档")
                // 配置作者信息
                .contact(new Contact().name("Diamond").url("https://github.com/JavaSmallPig").email("15399798037@163.com"))
                // 概述信息
                .summary("个人博客项目接口文档")
                // 配置版本号
                .version("V1.0");
    }

}
