package com.blog.exception;

import com.blog.exception.BaseException;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
