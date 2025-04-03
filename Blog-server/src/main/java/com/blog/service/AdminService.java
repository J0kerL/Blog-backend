package com.blog.service;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserPageQueryDTO;
import com.blog.entity.User;
import com.blog.result.PageResult;
import com.blog.vo.MenuVO;
import com.blog.vo.UserVO;

import java.util.List;

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
    UserVO getByName(String username);

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 员工分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult query(UserPageQueryDTO userPageQueryDTO);

    /**
     * 获取菜单
     * @return
     */
    List<MenuVO> getMenu();
}
