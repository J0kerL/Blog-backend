package com.blog.service.impl;

import com.blog.entity.User;
import com.blog.mapper.AdminMapper;
import com.blog.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:51
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User getByName(String username) {
        return adminMapper.getByName(username);
    }

}
