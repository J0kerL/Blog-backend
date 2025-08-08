package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:47
 * 创建评论失败异常
 */
public class CreateCommentFailException extends BaseException {

  public CreateCommentFailException() {
  }

    public CreateCommentFailException(String message) {
        super(message);
    }
}
