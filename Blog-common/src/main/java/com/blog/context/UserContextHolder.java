package com.blog.context;

/**
 * @Author Java小猪
 * @Date 2025/3/26 11:50
 * ThreadLocal
 */
public class UserContextHolder {
    // 定义一个 ThreadLocal 变量，用于存储当前线程的用户信息
    private static final ThreadLocal<Integer> THREADLOCAL = new ThreadLocal<>();

    // 设置当前线程的用户信息
    public static void setCurrentId(Integer userId) {
        THREADLOCAL.set(userId);
    }

    // 获取当前线程的用户信息
    public static Integer getCurrentId() {
        return THREADLOCAL.get();
    }

    // 清理当前线程的用户信息
    public static void removeCurrentId() {
        THREADLOCAL.remove();
    }
}
