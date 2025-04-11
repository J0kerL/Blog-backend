package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:47
 * 用户已存在异常
 */
public class AccountAlreadyExistException extends BaseException {

  public AccountAlreadyExistException() {
  }

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
