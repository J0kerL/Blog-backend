package com.blog.mapper;

import org.apache.ibatis.annotations.Insert;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
public interface UserMapper {

    /**
     * 注册
     * @param username
     * @param password
     * @param email
     * @return
     */
    @Insert("insert into user (username,password,email) values (#{username},#{password},#{email})")
    void register(String username, String password,String email);

}
