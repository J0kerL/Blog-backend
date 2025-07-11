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

    // 菜单ID
    private Integer id;
    // 菜单名称
    private String title;
    // 图标
    private String icon;
    // 前端路由路径
    private String path;
    // 前端组件路径
    private String component;
    // 父菜单ID（0为根）
    private Integer parentId;
    // 排序
    private Integer sort;
    // 是否隐藏 0-显示 1-隐藏
    private Integer hidden;

}
