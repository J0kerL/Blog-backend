package com.blog.service;

import com.blog.vo.MenuVO;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:24
 */
public interface MenuService {

    /**
     * 获取菜单
     * @return
     */
    List<MenuVO> getMenu();

}
