package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginVO {

    private Integer id;
    private String username;
    private String email;
    private String token;
}
