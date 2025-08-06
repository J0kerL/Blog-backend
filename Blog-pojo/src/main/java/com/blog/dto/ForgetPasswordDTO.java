package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/1/8
 * 忘记密码DTO
 */
@Data
@Schema(description = "忘记密码请求")
public class ForgetPasswordDTO implements Serializable {

    @Schema(description = "用户名或邮箱")
    private String account;

    @Schema(description = "新密码")
    private String newPassword;

    @Schema(description = "确认新密码")
    private String confirmPassword;
}