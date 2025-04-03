package com.blog.service.impl;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserPageQueryDTO;
import com.blog.entity.User;
import com.blog.exception.AccountLockedException;
import com.blog.exception.AccountNotFoundException;
import com.blog.exception.NotHavePowerException;
import com.blog.exception.PasswordErrorException;
import com.blog.mapper.AdminMapper;
import com.blog.result.PageResult;
import com.blog.service.AdminService;
import com.blog.vo.MenuVO;
import com.blog.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.blog.constant.Constant.*;

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
    public UserVO getByName(String username) {
        User user = adminMapper.getByName(username);
        if (user == null) {
            throw new AccountNotFoundException(USER_NOT_FOUND);
        }
        return UserVO.builder()
                .username(user.getUsername())
                .password("******")
                .status(user.getStatus())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .role(user.getRole())
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
        // 可以用户名和密码登录 也可以邮箱和密码登录
        // 获取用户输入的用户名/邮箱和密码
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        // 1、根据account查询数据库中的数据
        User user = adminMapper.getUser(account);
        // 2、处理各种异常情况（用户名/邮箱不存在、密码不对、账号被锁定）
        // 3、除了用户登录需要校验的情况外，还有校验一种情况，是否为管理员
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(USER_NOT_FOUND);
        }
        // 没有权限
        if (!user.getRole().equals(SUPER_USER)) {
            throw new NotHavePowerException(NOT_HAVE_POWER);
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
     * 员工分页查询
     *
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult query(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<User> page = adminMapper.query(userPageQueryDTO);
        long total = page.getTotal();
        List<User> list = page.getResult();
        list.forEach(user -> {
            user.setPassword("******");
        });
        return new PageResult(total, list);
    }

    /**
     * 获取菜单
     *
     * @return
     */
    @Override
    public List<MenuVO> getMenu() {
        List<MenuVO> list = adminMapper.getMenu();
        if (!list.isEmpty()) {
            return list;
        } else {
            return null;
        }
    }

}
