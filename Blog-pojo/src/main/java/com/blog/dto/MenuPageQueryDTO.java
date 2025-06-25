package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/14 15:05
 */
@Data
@Schema(description = "菜单分页查询DTO")
public class MenuPageQueryDTO implements Serializable {

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "每页记录数")
    private Integer pageSize;

    @Schema(description = "菜单标题")
    private String title;

    @Schema(description = "菜单名称")
    private String name;

}