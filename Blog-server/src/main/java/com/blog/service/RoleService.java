package com.blog.service;

import com.blog.dto.RolePageQueryDTO;
import com.blog.result.PageResult;

/**
 * @Author Java小猪
 * @Date 2025/4/3 16:03
 */
public interface RoleService {

    /**
     * 角色分页查询
     * @param rolePageQueryDTO
     * @return
     */
    PageResult page(RolePageQueryDTO rolePageQueryDTO);
}
