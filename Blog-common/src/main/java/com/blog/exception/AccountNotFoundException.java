package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:44
 * 账号不存在异常
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
