package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/3 14:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {

    private Integer id;
    private String title;
    private String icon;
    private String path;
    private String component;
    private Integer parentId;
    private Integer sort;
    private Integer hidden;
    private List<MenuVO> children;

}
