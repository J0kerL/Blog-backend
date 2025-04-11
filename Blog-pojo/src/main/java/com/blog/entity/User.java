package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/21 16:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String avatar;
    private String email;
    private Integer sex;
    private Integer roleId;

}
