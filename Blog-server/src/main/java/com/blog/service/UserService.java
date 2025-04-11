package com.blog.service;

import com.blog.dto.*;
import com.blog.entity.User;
import com.blog.result.PageResult;
import com.blog.vo.MenuVO;
import com.blog.vo.UserVO;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    UserVO getByName(String username);

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

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult query(UserPageQueryDTO userPageQueryDTO);

    /**
     * 获取菜单
     * @return
     */
    List<MenuVO> getMenu();

    /**
     * 新增用户
     * @param addUserDTO
     */
    void addUser(AddUserDTO addUserDTO);
}
