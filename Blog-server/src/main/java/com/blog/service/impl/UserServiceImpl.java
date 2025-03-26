package com.blog.service.impl;

import com.blog.constant.Constant;
import com.blog.dto.UserDTO;
import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import com.blog.exception.AccountLockedException;
import com.blog.exception.AccountNotFoundException;
import com.blog.exception.PasswordErrorException;
import com.blog.mapper.AdminMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import com.blog.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;

    /**
     * 注册
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserVO register(UserDTO userDTO) {
        // 根据用户名查询用户信息
        User user = adminMapper.getByName(userDTO.getUsername());
        // 用户名已存在 不能注册
        if (user != null) {
            return null;
        }
        // 不存在 注册
        userMapper.register(userDTO.getUsername(), userDTO.getPassword());
        // 不返回明文密码
        return UserVO.builder()
                .username(userDTO.getUsername())
                .password("******")
                .status(1)
                .role(1)
                .build();
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        //1、根据用户名查询数据库中的数据
        User user = adminMapper.getByName(username);
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw  new AccountNotFoundException(Constant.USER_NOT_FOUND);
        }
        //密码比对
        if (!user.getPassword().equals(password)) {
            //密码错误
            throw new PasswordErrorException(Constant.PASSWORD_ERROR);
        }
        //账号被锁定
        if (user.getStatus().equals(Constant.DISABLE)){
            throw new AccountLockedException(Constant.ACCOUNT_LOCKED);
        }
        //3、返回实体对象
        return user;
    }

}
