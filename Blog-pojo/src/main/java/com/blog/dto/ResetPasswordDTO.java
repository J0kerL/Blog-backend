package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/1/8
 * 重置密码DTO
 */
@Data
@Schema(description = "重置密码请求")
public class ResetPasswordDTO implements Serializable {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "新密码")
    private String newPassword;
}