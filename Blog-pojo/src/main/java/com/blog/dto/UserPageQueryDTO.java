package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/1 17:59
 */
@Data
@Schema(description = "用户分页查询")
public class UserPageQueryDTO implements Serializable {

    // 员工姓名
    private String username;

    // 账号状态
    private Integer status;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;
}
