package com.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Java小猪
 * @Date 2025/4/10 14:13
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                // 分组名称
                .group("DiamondBlog-API")
                // 指定扫描的控制器包路径
                .packagesToScan("com.blog.controller")
                // 匹配所有接口路径
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Diamond博客接口文档")
                        .description("一个专注于技术分享的博客平台")
                        .version("V1.0")
                        .contact(new Contact().name("Diamond").email("15399798037@163.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

}
