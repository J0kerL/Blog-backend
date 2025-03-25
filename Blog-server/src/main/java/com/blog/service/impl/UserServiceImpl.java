package com.blog.service.impl;

import com.blog.dto.UserDTO;
import com.blog.entity.User;
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

}
