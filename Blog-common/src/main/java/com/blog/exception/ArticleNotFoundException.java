package com.blog.exception;

/**
 * @author Diamond
 * @create 2025-06-05 18:03
 * 文章不存在异常
 */
public class ArticleNotFoundException extends BaseException {

    public ArticleNotFoundException() {
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
