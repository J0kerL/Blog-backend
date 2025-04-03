package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/3 15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements Serializable {

    private Integer id;
    // 角色编码
    private String code;
    // 角色名称
    private String name;
    // 备注
    private String remarks;

}
