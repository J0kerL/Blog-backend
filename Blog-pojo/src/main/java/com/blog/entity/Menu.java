package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Java小猪
 * @Date 2025/4/3 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {

    private Integer id;
    private String path;
    private String component;
    private String title;
    private Integer sort;
    private String icon;
    private String redirect;
    private String name;
    private Integer hidden;
    private Integer isExternal;

}
