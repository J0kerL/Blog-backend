package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:57
 * 没有管理权限异常
 */
public class NotHavePowerException extends BaseException {

    public NotHavePowerException() {
    }

    public NotHavePowerException(String message) {
        super(message);
    }
}
