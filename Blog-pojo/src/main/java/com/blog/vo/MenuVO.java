package com.blog.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author Java小猪
 * @Date 2025/4/3 14:38
 */
@Data
@Builder
public class MenuVO {

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
