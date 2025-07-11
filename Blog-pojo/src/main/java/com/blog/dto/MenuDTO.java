package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/14 15:00
 */
@Data
@Schema(description = "菜单DTO")
public class MenuDTO implements Serializable {

    @Schema(description = "菜单ID")
    private Integer id;

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 20, message = "菜单名称长度不能超过20个字符")
    @Schema(description = "菜单名称")
    private String title;

    @Size(max = 30, message = "图标长度不能超过30个字符")
    @Schema(description = "图标")
    private String icon;

    @NotBlank(message = "前端路由路径不能为空")
    @Size(max = 100, message = "前端路由路径长度不能超过100个字符")
    @Schema(description = "前端路由路径")
    private String path;

    @NotBlank(message = "前端组件路径不能为空")
    @Size(max = 100, message = "前端组件路径长度不能超过100个字符")
    @Schema(description = "前端组件路径")
    private String component;

    @Schema(description = "父菜单ID（0为根）")
    private Integer parentId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否隐藏 0-显示 1-隐藏")
    private Integer hidden;

}