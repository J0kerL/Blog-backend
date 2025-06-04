package com.blog.constant;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:26
 * 常量类
 */
public class Constant {

    // 正常
    public static final Integer ENABLE = 1;
    // 禁用
    public static final Integer DISABLE = 0;
    // 男
    public static final Integer MAN = 1;
    // 女
    public static final Integer WOMAN = 2;
    // 沃尔玛购物袋
    public static final Integer MIDDLE = 0;
    // 普通用户
    public static final Integer NORMAL_USER = 3;
    // 超级管理员
    public static final Integer SUPER_ADMIN = 1;
    // 管理员
    public static final Integer ADMIN = 2;
    public static final String ALREADY_EXISTS = "用户已存在";
    public static final String USER_NOT_FOUND = "用户不存在";
    public static final String PASSWORD_ERROR = "密码错误";
    public static final String SUCCESS_REGISTER = "注册成功";
    public static final String SUCCESS_LOGIN = "登录成功";
    public static final String ACCOUNT_LOCKED = "账号被锁定";
    public static final String USER_ID = "userId";
    public static final String UNKNOWN_ERROR = "未知错误";
    public static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final Integer LENGTH = 6;
    public static final String CODE = "Diamond-Blog 注册验证码";
    public static final String YOUR_CODE = "您的验证码是：";
    public static final String EXPIRATION_DATE = "，有效期 5 分钟，请勿泄露。";
    public static final String CODE_KEY = "captcha:";
    public static final String ALREADY_EXIT = "已成功退出登录";
    public static final String CODE_PASS = "验证码已过期";
    public static final String CODE_ERROR = "验证码错误";
    public static final String NOT_HAVE_POWER = "没有权限";
    public static final String TOKEN_ERROR = "token错误";
    public static final String TOKEN_IS_NULL = "JWT令牌为空，请求被拒绝";
    public static final String OPERATE_SUCCESS = "操作成功";
    public static final String AVATAR_URL = "https://diamond-blog.oss-cn-beijing.aliyuncs.com/defaultAvatar.jpg";


}
