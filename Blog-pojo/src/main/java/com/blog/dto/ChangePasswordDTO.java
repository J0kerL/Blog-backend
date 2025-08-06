package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/1/8
 * 修改密码DTO
 */
@Data
@Schema(description = "修改密码请求")
public class ChangePasswordDTO implements Serializable {

    @Schema(description = "原密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

    @Schema(description = "确认新密码")
    private String confirmPassword;
}