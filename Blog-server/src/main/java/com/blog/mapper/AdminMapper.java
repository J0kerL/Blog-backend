package com.blog.mapper;

import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import org.apache.ibatis.annotations.Insert;
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
     * 注册
     * @param userLoginDTO
     * @return
     */
    @Insert("insert into user(username,password,status,role) values (#{userLoginDTO.getUsername},#{userLoginDTO.getPassword},#{userLoginDTO.getStatus},#{userLoginDTO.getRole})")
    UserLoginDTO register(UserLoginDTO userLoginDTO);
}
