package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:47
 * 评论内容不能为空异常
 */
public class CommentNotNullException extends BaseException {

  public CommentNotNullException() {
  }

    public CommentNotNullException(String message) {
        super(message);
    }
}
