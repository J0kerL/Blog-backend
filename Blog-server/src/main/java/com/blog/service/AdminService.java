package com.blog.service;

import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:51
 */
public interface AdminService {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User getByName(String username);

    /**
     * 注册
     * @param userLoginDTO
     * @return
     */
    UserLoginDTO register(UserLoginDTO userLoginDTO);

}
