package com.blog.utils;

import java.util.Random;

import static com.blog.constant.Constant.CHARACTERS;
import static com.blog.constant.Constant.LENGTH;

/**
 * @Author Java小猪
 * @Date 2025/3/28 14:47
 * 生成6位随机数的验证码
 */
public class CaptchaUtil {

    public static String generateCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            captcha.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return captcha.toString();
    }

}
