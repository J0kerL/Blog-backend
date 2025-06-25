package com.blog.mapper;

import com.blog.dto.UserDTO;
import com.blog.dto.UserPageQueryDTO;
import com.blog.dto.UserRegisterDTO;
import com.blog.entity.User;
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
     * 根据用户名或邮箱查询用户信息
     * @param account
     * @return
     */
    @Select("select *from user where username = #{account} or email = #{account}")
    User getByAccount(String account);

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
     * 新增用户
     * @param user
     */
    @Insert("insert into user (username, password, status, avatar, email, sex, role_id) VALUES" +
            "(#{username},#{password},#{status},#{avatar},#{email},#{sex},#{roleId})")
    void addUser(User user);

    /**
     * 修改用户
     * @param userDTO
     */
    void updateUser(UserDTO userDTO);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    @Select("select username from user where id = #{id}")
    User getById(Integer id);

    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据角色ID查询用户数量
     * @param roleId
     * @return
     */
    @Select("select count(*) from user where role_id = #{roleId}")
    int countByRoleId(Integer roleId);
}
