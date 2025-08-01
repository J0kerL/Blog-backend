package com.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.blog.dto.AddUserDTO;
import com.blog.dto.UserDTO;
import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserPageQueryDTO;
import com.blog.dto.UserRegisterDTO;
import com.blog.entity.User;
import com.blog.exception.AccountAlreadyExistException;
import com.blog.exception.AccountLockedException;
import com.blog.exception.AccountNotFoundException;
import com.blog.exception.PasswordErrorException;
import com.blog.mapper.UserMapper;
import com.blog.result.PageResult;
import com.blog.service.UserService;
import com.blog.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.blog.constant.Constant.*;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名或邮箱查询用户信息
     *
     * @param account
     * @return
     */
    @Override
    public UserVO getByAccount(String account) {
        User user = userMapper.getByAccount(account);
        if (user == null) {
            throw new AccountNotFoundException(USER_NOT_FOUND);
        }
        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password("******")
                .status(user.getStatus())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .sex(user.getSex())
                .roleId(user.getRoleId())
                .build();
    }

    /**
     * 注册
     *
     * @param userRegisterDTO
     * @return
     */
    @Override
    public UserVO register(UserRegisterDTO userRegisterDTO) {
        // 判断用户是否存在
        if (userMapper.getByAccount(userRegisterDTO.getUsername()) != null) {
            return null;
        }
        // 不存在 进行新增操作
        User user = new User();
        BeanUtil.copyProperties(userRegisterDTO, user);
        user.setStatus(ENABLE);
        // 设置默认头像
        user.setAvatar(AVATAR_URL);
        user.setSex(MAN);
        user.setRoleId(NORMAL_USER);
        userMapper.register(user);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        // 可以用户名和密码登录 也可以邮箱和密码登录
        // 获取用户输入的用户名/邮箱和密码
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        //1、根据account查询数据库中的数据
        User user = userMapper.getByAccount(account);
        //2、处理各种异常情况（用户名/邮箱不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(USER_NOT_FOUND);
        }
        //密码比对
        if (!user.getPassword().equals(password)) {
            //密码错误
            throw new PasswordErrorException(PASSWORD_ERROR);
        }
        //账号被锁定
        if (user.getStatus().equals(DISABLE)) {
            throw new AccountLockedException(ACCOUNT_LOCKED);
        }
        //3、返回实体对象
        return user;
    }

    /**
     * 用户分页查询
     *
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult query(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<User> page = userMapper.query(userPageQueryDTO);
        long total = page.getTotal();
        List<User> list = page.getResult();
        list.forEach(user -> {
            user.setPassword("******");
        });
        return new PageResult(total, list);
    }

    /**
     * 新增用户
     * @param addUserDTO
     */
    @Override
    public void addUser(AddUserDTO addUserDTO) {
        // 判断用户是否存在
        // 存在 返回异常信息
        if (userMapper.getByAccount(addUserDTO.getUsername()) != null) {
            throw new AccountAlreadyExistException(ALREADY_EXISTS);
        }
        // 不存在 进行新增操作
        User user = new User();
        BeanUtil.copyProperties(addUserDTO, user);
        
        // 设置默认值（如果前端没有传递）
        if (user.getStatus() == null) {
            user.setStatus(ENABLE);
        }
        if (user.getSex() == null) {
            user.setSex(MAN);
        }
        if (user.getRoleId() == null) {
            user.setRoleId(NORMAL_USER);
        }
        
        // 设置默认头像
        user.setAvatar(AVATAR_URL);
        
        userMapper.register(user);
    }

    /**
     * 修改用户
     * @param userDTO
     */
    @Override
    public void updateUser(UserDTO userDTO) {
        if (userMapper.getById(userDTO.getId()) == null) {
            throw new AccountNotFoundException(USER_NOT_FOUND);
        }
        
        // 创建更新DTO，处理特殊字段
        UserDTO updateDTO = new UserDTO();
        BeanUtil.copyProperties(userDTO, updateDTO);
        
        // 如果密码为空，则不更新密码字段
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            updateDTO.setPassword(null);
        }
        
        // 如果没有上传新头像，保持原头像不变
        if (userDTO.getAvatar() == null || userDTO.getAvatar().equals(AVATAR_URL)) {
            updateDTO.setAvatar(null);
        }
        
        userMapper.updateUser(updateDTO);
    }

    /**
     * 根据id批量删除用户
     * @param ids
     */
    @Override
    @Transactional
    public void deleteByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 检查所有用户是否存在
        for (Integer id : ids) {
            if (userMapper.getById(id) == null) {
                throw new AccountNotFoundException(USER_NOT_FOUND);
            }
        }
        // 批量删除用户
        userMapper.deleteByIds(ids);
    }
}

