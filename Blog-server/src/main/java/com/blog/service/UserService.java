package com.blog.service;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserRegisterDTO;
import com.blog.entity.User;
import com.blog.vo.UserVO;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
public interface UserService {

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    UserVO register(UserRegisterDTO userRegisterDTO);

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 发送验证码
     * @param email
     * @return
     */
    String sendCaptchaEmail(String email);
}
