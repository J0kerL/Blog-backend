package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/24 17:00
 */
@Data
@Schema(description = "用户")
public class UserDTO implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String avatar;
    private String email;
    private Integer sex;
    private Integer roleId;

}
