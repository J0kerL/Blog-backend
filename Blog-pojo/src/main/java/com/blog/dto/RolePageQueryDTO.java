package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/3 15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePageQueryDTO implements Serializable {

    // 角色名称
    private String name;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;

}
