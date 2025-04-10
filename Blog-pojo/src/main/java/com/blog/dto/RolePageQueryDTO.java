package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/3 15:55
 */
@Data
@Schema(description = "角色分页查询")
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
