package com.blog.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/4/1 17:59
 */
@Data
public class UserPageQueryDTO implements Serializable {

    //员工姓名
    //private String name;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}
