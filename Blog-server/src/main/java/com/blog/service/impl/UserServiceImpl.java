package com.blog.service.impl;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserRegisterDTO;
import com.blog.entity.User;
import com.blog.exception.AccountLockedException;
import com.blog.exception.AccountNotFoundException;
import com.blog.exception.PasswordErrorException;
import com.blog.mapper.AdminMapper;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import com.blog.utils.CaptchaUtil;
import com.blog.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String AVATAR_URL = "https://diamond-blog.oss-cn-beijing.aliyuncs.com/defaultAvatar.jpg";

    @Value("${spring.mail.username}")
    public String emailAddress;

    /**
     * 注册
     *
     * @param userRegisterDTO
     * @return
     */
    @Override
    public UserVO register(UserRegisterDTO userRegisterDTO) {
        // 根据用户名查询用户信息
        User user = adminMapper.getByName(userRegisterDTO.getUsername());
        // 用户名已存在 不能注册
        if (user != null) {
            return null;
        }
        // 不存在 注册
        userMapper.register(userRegisterDTO.getUsername(), userRegisterDTO.getPassword(), userRegisterDTO.getEmail());
        // 不返回明文密码
        return UserVO.builder()
                .username(userRegisterDTO.getUsername())
                .password("******")
                .status(1)
                .avatar(AVATAR_URL)
                .email(userRegisterDTO.getEmail())
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
        // 可以用户名和密码登录 也可以邮箱和密码登录
        // 获取用户输入的用户名/邮箱和密码
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        //1、根据account查询数据库中的数据
        User user = adminMapper.getUser(account);
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
     * 发送验证码
     *
     * @param email
     * @return
     */
    @Override
    public String sendCaptchaEmail(String email) {
        // 生成验证码
        String captcha = CaptchaUtil.generateCaptcha();
        // 存储到 Redis，设置 5 分钟过期
        redisTemplate.opsForValue().set(CODE_KEY + email, captcha, 5, TimeUnit.MINUTES);
        // 发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人
        message.setFrom(emailAddress);
        // 收件人
        message.setTo(email);
        message.setSubject(CODE);
        message.setText(YOUR_CODE + captcha + EXPIRATION_DATE);
        mailSender.send(message);
        log.info("验证码为：{}", redisTemplate.opsForValue().get(CODE_KEY + email));
        return message.getText();
    }
}

