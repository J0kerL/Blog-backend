package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:47
 * 密码错误异常
 */
public class PasswordErrorException extends BaseException {

  public PasswordErrorException() {
  }

    public PasswordErrorException(String message) {
        super(message);
    }
}
