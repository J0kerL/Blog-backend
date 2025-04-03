package com.blog.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:08
 */
@Data
public class Result<T> implements Serializable {

    //状态码
    private Integer code;
    //错误信息
    private String msg;
    //数据
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 200;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.code = 200;
        result.data = object;
        return result;
    }

    public static <T> Result<T> success(String msg,T object) {
        Result<T> result = new Result<T>();
        result.code = 200;
        result.msg = msg;
        result.data = object;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> error(Integer code,String msg) {
        Result<T> result = new Result<T>();
        result.code = code;
        result.msg = msg;
        return result;
    }

}
