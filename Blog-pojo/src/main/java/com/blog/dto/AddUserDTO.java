package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/11 11:06
 */
@Data
@Schema(description = "新增用户")
public class AddUserDTO implements Serializable {

    private String username;
    private String password;
    private String email;

}
