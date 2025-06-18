package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/3 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu implements Serializable {

    // 菜单逐渐id
    private Integer id;
    // 菜单标题
    private String title;
    // 菜单名称
    private String name;
    // 前端路由路径
    private String path;
    // 前端组件
    private String component;
    // 菜单图标
    private String icon;
    // 排序
    private Integer sort;
    // 是否外链 0-否 1-是
    private Integer isExternal;

}
