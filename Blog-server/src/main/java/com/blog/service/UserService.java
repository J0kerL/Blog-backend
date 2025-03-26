package com.blog.service;

import com.blog.dto.UserDTO;
import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import com.blog.vo.UserVO;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
public interface UserService {

    /**
     * 注册
     * @param userDTO
     * @return
     */
    UserVO register(UserDTO userDTO);

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);
}
