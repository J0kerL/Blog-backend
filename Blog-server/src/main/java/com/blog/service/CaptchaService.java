package com.blog.service;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:13
 */
public interface CaptchaService {

    /**
     * 发送验证码
     * @param email
     * @return
     */
    String sendCaptchaEmail(String email);

}
