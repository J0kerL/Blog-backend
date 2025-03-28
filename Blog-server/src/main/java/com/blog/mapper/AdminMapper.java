package com.blog.mapper;

import com.blog.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:51
 */
public interface AdminMapper {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Select("select *from user where username = #{username}")
    User getByName(String username);

    /**
     * 登录查询用户
     * @param account
     * @return
     */
    @Select("select *from user where username = #{account} or email = #{account}")
    User getUser(String account);
}
