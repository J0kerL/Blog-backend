package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/24 17:00
 */
@Data
@Schema(description = "用户注册")
public class UserRegisterDTO implements Serializable {

    private String username;
    private String password;
    private String email;
    private String code;

}
