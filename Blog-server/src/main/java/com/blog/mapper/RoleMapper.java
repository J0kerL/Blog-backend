package com.blog.mapper;

import com.blog.dto.RolePageQueryDTO;
import com.blog.entity.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 新增角色
     *
     * @param role
     */
    void insert(Role role);

    /**
     * 修改角色
     *
     * @param role
     */
    void update(Role role);

    /**
     * 根据id删除角色
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id批量删除角色
     *
     * @param ids
     */
    void deleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据id查询角色
     *
     * @param id
     * @return
     */
    Role getById(Integer id);

    /**
     * 根据角色编码查询角色
     *
     * @param code
     * @return
     */
    Role getByCode(String code);
}
