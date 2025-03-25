package com.blog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Java小猪
 * @Date 2025/3/25 17:19
 */
@Component
@ConfigurationProperties(prefix = "blog.jwt")
@Data
public class JwtProperties {

    /**
     * 生成jwt令牌相关配置
     */
    private String secretKey;
    private long ttl;
    private String tokenName;

}
