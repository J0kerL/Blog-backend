package com.blog.service.impl;

import com.blog.dto.RolePageQueryDTO;
import com.blog.entity.Role;
import com.blog.mapper.RoleMapper;
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
}
