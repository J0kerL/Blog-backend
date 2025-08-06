package com.blog.service;

import com.blog.dto.*;
import com.blog.entity.User;
import com.blog.result.PageResult;
import com.blog.vo.UserStatsVO;
import com.blog.vo.UserVO;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
public interface UserService {

    /**
     * 根据用户名或邮箱查询用户信息
     * @param account
     * @return
     */
    UserVO getByAccount(String account);

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
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult query(UserPageQueryDTO userPageQueryDTO);

    /**
     * 新增用户
     * @param addUserDTO
     */
    void addUser(AddUserDTO addUserDTO);

    /**
     * 修改用户
     * @param userDTO
     */
    void updateUser(UserDTO userDTO);

    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 获取用户个人统计数据
     * @param userId
     * @return
     */
    UserStatsVO getUserStats(Integer userId);

    /**
     * 修改密码
     * @param changePasswordDTO
     * @param userId
     */
    void changePassword(ChangePasswordDTO changePasswordDTO, Integer userId);

    /**
     * 重置用户密码（管理员功能）
     * @param resetPasswordDTO
     */
    void resetPassword(ResetPasswordDTO resetPasswordDTO);

    /**
     * 忘记密码 - 通过账号重置密码
     * @param forgetPasswordDTO
     */
    void forgetPassword(ForgetPasswordDTO forgetPasswordDTO);
}
