package com.blog.service.impl;

import com.blog.dto.UserLoginDTO;
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

    /**
     * 注册
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public UserLoginDTO register(UserLoginDTO userLoginDTO) {
        // 根据用户名查询用户信息
        User user = adminMapper.getByName(userLoginDTO.getUsername());
        // 用户名已存在 不能注册
        if (user != null) {
            return null;
        }
        // 不存在 注册
        UserLoginDTO newUserLoginDTO = adminMapper.register(userLoginDTO);
        if (newUserLoginDTO != null) {
            // 返回密码密文显示
            newUserLoginDTO.setPassword("******");
        }
        return newUserLoginDTO;
    }

}
