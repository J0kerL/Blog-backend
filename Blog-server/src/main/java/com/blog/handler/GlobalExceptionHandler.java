package com.blog.handler;

import com.blog.exception.BaseException;
import com.blog.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static com.blog.constant.Constant.ALREADY_EXISTS;
import static com.blog.constant.Constant.UNKNOWN_ERROR;

/**
 * @Author Java小猪
 * @Date 2025/3/26 10:10
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 处理SQL异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String msg = split[2] + ALREADY_EXISTS;
            return Result.error(msg);
        }
        return Result.error(UNKNOWN_ERROR);
    }

    /**
     * 处理所有SQL相关的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({SQLException.class, DataAccessException.class, BadSqlGrammarException.class})
    public Result<String> handleSQLException(Exception ex) {
        log.error("SQL异常：", ex);
        String errorMessage = ex.getMessage();
        if (errorMessage != null && errorMessage.contains("SQL")) {
            return Result.error("数据库操作异常：" + errorMessage);
        }
        return Result.error("数据库操作异常：" + UNKNOWN_ERROR);
    }
}
