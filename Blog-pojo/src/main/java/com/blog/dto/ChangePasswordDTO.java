package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/25 18:00
 */
@Data
@Schema(description = "修改密码")
public class ChangePasswordDTO implements Serializable {

    @Schema(description = "用户ID")
    private Integer id;
    
    @Schema(description = "旧密码")
    private String oldPassword;
    
    @Schema(description = "新密码")
    private String newPassword;

}