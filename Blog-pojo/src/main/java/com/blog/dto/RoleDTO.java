package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/3 16:30
 */
@Data
@Schema(description = "角色")
public class RoleDTO implements Serializable {

    private Integer id;
    // 角色编码
    private String code;
    // 角色名称
    private String name;
    // 备注
    private String remarks;

}