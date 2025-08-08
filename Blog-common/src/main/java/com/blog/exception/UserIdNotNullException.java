package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:47
 * 用户id不能为空异常
 */
public class UserIdNotNullException extends BaseException {

  public UserIdNotNullException() {
  }

    public UserIdNotNullException(String message) {
        super(message);
    }
}
