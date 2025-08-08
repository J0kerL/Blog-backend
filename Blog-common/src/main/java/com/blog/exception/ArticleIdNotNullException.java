package com.blog.exception;

/**
 * @Author Java小猪
 * @Date 2025/3/26 9:47
 * 文章id不能为空异常
 */
public class ArticleIdNotNullException extends BaseException {

  public ArticleIdNotNullException() {
  }

    public ArticleIdNotNullException(String message) {
        super(message);
    }
}
