package com.blog.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/24 17:00
 */
@Data
public class UserRegisterDTO implements Serializable {

    private String username;
    private String password;
    private String email;
    private String code;

}
