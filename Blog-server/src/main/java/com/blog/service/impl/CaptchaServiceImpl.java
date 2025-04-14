package com.blog.service.impl;

import com.blog.service.CaptchaService;
import com.blog.utils.CaptchaUtil;
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
 * @Date 2025/4/14 14:14
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.mail.username}")
    public String emailAddress;

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
