package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "菜单标题不能为空")
    @Size(max = 100, message = "菜单标题长度不能超过100个字符")
    @Schema(description = "菜单标题")
    private String title;

    @Size(max = 100, message = "菜单名称长度不能超过100个字符")
    @Schema(description = "菜单名称")
    private String name;

    @NotBlank(message = "路由路径不能为空")
    @Size(max = 255, message = "路由路径长度不能超过255个字符")
    @Schema(description = "前端路由路径")
    private String path;

    @Size(max = 255, message = "前端组件长度不能超过255个字符")
    @Schema(description = "前端组件")
    private String component;

    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    @Schema(description = "菜单图标")
    private String icon;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否外链 0-否 1-是")
    private Integer isExternal;

}