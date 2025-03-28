package com.blog.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author Java小猪
 * @Date 2025/3/28 17:43
 */
@Configuration  // 标记这是一个Spring配置类
public class RedisConfig {

    // 定义一个Bean，返回自定义配置的RedisTemplate
    @Bean
    @SuppressWarnings("all")  // 抑制所有警告，通常用于避免序列化相关的警告
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate实例，指定key为String类型，value为Object类型
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置Redis连接工厂，由Spring注入
        template.setConnectionFactory(redisConnectionFactory);

        // 配置JSON序列化器，用于处理复杂对象
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 创建Jackson的对象映射器
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置属性可见性：所有属性都可见
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 启用默认类型信息，存储对象类型，便于反序列化
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 将配置好的ObjectMapper应用到JSON序列化器
        jsonRedisSerializer.setObjectMapper(objectMapper);

        // 配置String序列化器，用于处理字符串
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 设置key的序列化方式为String
        template.setKeySerializer(stringRedisSerializer);
        // 设置hash类型的key的序列化方式为String
        template.setHashKeySerializer(stringRedisSerializer);

        // 设置value的序列化方式为JSON
        template.setValueSerializer(jsonRedisSerializer);
        // 设置hash类型的value的序列化方式为JSON
        template.setHashValueSerializer(jsonRedisSerializer);

        // 初始化RedisTemplate的配置
        template.afterPropertiesSet();

        return template;  // 返回配置好的RedisTemplate实例
    }
}
