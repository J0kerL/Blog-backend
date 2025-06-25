package com.blog.service;

import com.blog.dto.RoleDTO;
import com.blog.dto.RolePageQueryDTO;
import com.blog.result.PageResult;

import java.util.List;

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

    /**
     * 新增角色
     * @param roleDTO
     */
    void addRole(RoleDTO roleDTO);

    /**
     * 修改角色
     * @param roleDTO
     */
    void updateRole(RoleDTO roleDTO);

    /**
     * 根据id批量删除角色
     * @param ids
     */
    void deleteByIds(List<Integer> ids);
}
