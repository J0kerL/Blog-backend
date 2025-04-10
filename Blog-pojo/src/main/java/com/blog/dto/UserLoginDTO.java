package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:37
 */
@Data
@Schema(description = "用户登录")
public class UserLoginDTO implements Serializable {

    private String account;
    private String password;

}
