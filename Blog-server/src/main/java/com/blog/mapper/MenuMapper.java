package com.blog.mapper;

import com.blog.vo.MenuVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:25
 */
public interface MenuMapper {

    /**
     * 获取菜单
     * @return
     */
    @Select("select *from menu")
    List<MenuVO> getMenu();

}
