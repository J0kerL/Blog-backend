package com.blog.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:04
 */
@Data
@Builder
public class UserVO {

    private String username;
    private String password;
    private int status;
    private int role;

}
