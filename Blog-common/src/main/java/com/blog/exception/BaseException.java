package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:43
 * 业务异常
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }
}
