package com.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @Author Java小猪
 * @Date 2025/4/3 17:00
 */
@Data
@Schema(description = "标签DTO")
public class TagDTO {

    @Schema(description = "标签ID")
    private Integer id;

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称长度不能超过50个字符")
    @Schema(description = "标签名称")
    private String name;

}