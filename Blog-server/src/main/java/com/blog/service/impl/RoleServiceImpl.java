package com.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.blog.dto.RoleDTO;
import com.blog.dto.RolePageQueryDTO;
import com.blog.entity.Role;
import com.blog.exception.BaseException;
import com.blog.mapper.RoleMapper;
import com.blog.mapper.UserMapper;
import com.blog.result.PageResult;
import com.blog.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/3 16:03
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    
    @Resource
    private UserMapper userMapper;

    /**
     * 角色分页查询
     *
     * @param rolePageQueryDTO
     * @return
     */
    @Override
    public PageResult page(RolePageQueryDTO rolePageQueryDTO) {
        PageHelper.startPage(rolePageQueryDTO.getPage(), rolePageQueryDTO.getPageSize());
        Page<Role> page = roleMapper.page(rolePageQueryDTO);
        long total = page.getTotal();
        List<Role> list = page.getResult();
        return new PageResult(total, list);
    }

    /**
     * 新增角色
     *
     * @param roleDTO
     */
    @Override
    public void addRole(RoleDTO roleDTO) {
        // 检查角色编码是否已存在
        if (roleMapper.getByCode(roleDTO.getCode()) != null) {
            throw new BaseException("角色编码已存在");
        }
        
        Role role = new Role();
        BeanUtil.copyProperties(roleDTO, role);
        roleMapper.insert(role);
    }

    /**
     * 修改角色
     *
     * @param roleDTO
     */
    @Override
    public void updateRole(RoleDTO roleDTO) {
        // 检查角色是否存在
        if (roleMapper.getById(roleDTO.getId()) == null) {
            throw new BaseException("角色不存在");
        }
        
        // 检查角色编码是否被其他角色使用
        Role existRole = roleMapper.getByCode(roleDTO.getCode());
        if (existRole != null && !existRole.getId().equals(roleDTO.getId())) {
            throw new BaseException("角色编码已存在");
        }
        
        Role role = new Role();
        BeanUtil.copyProperties(roleDTO, role);
        roleMapper.update(role);
    }

    /**
     * 根据id批量删除角色
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BaseException("删除的角色id不能为空");
        }
        
        // 检查角色是否被用户使用
        for (Integer roleId : ids) {
            int userCount = userMapper.countByRoleId(roleId);
            if (userCount > 0) {
                Role role = roleMapper.getById(roleId);
                String roleName = role != null ? role.getName() : "ID为" + roleId;
                throw new BaseException("角色【" + roleName + "】正在被用户使用，无法删除");
            }
        }
        
        roleMapper.deleteByIds(ids);
    }
}
