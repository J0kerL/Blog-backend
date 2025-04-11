package com.blog.mapper;

import com.blog.dto.RolePageQueryDTO;
import com.blog.entity.Role;
import com.github.pagehelper.Page;

/**
 * @Author Java小猪
 * @Date 2025/4/3 16:04
 */
public interface RoleMapper {
    /**
     * 角色分页查询
     *
     * @param rolePageQueryDTO
     * @return
     */
    Page<Role> page(RolePageQueryDTO rolePageQueryDTO);
}
