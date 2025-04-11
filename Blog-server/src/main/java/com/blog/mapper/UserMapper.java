package com.blog.mapper;

import com.blog.dto.UserPageQueryDTO;
import com.blog.dto.UserRegisterDTO;
import com.blog.entity.User;
import com.blog.vo.MenuVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/3/25 16:31
 */
public interface UserMapper {

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

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    @Insert("insert into user (username,password,email) values (#{username},#{password},#{email})")
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    Page<User> query(UserPageQueryDTO userPageQueryDTO);

    /**
     * 获取菜单
     * @return
     */
    @Select("select *from menu")
    List<MenuVO> getMenu();

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into user (username, password, status, avatar, email, sex, roleId) VALUES" +
            "(#{username},#{password},#{status},#{avatar},#{email},#{sex},#{roleId})")
    void addUser(User user);
}
