package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:57
 * 账号被锁定异常
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String message) {
        super(message);
    }
}
