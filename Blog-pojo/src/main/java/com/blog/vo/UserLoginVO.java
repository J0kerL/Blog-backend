package com.blog.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:35
 */
@Data
@Builder
public class UserLoginVO {

    private Integer id;
    private String username;
    private String email;
    private String token;
}
