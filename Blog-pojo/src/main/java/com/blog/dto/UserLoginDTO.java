package com.blog.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:37
 */
@Data
public class UserLoginDTO implements Serializable {

    private String username;
    private String password;

}
