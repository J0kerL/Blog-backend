package com.blog.service;

import com.blog.dto.MenuDTO;
import com.blog.dto.MenuPageQueryDTO;
import com.blog.entity.Menu;
import com.blog.result.PageResult;
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

    /**
     * 分页查询菜单
     * @param menuPageQueryDTO
     * @return
     */
    PageResult pageQuery(MenuPageQueryDTO menuPageQueryDTO);

    /**
     * 新增菜单
     * @param menuDTO
     */
    void save(MenuDTO menuDTO);

    /**
     * 根据ID查询菜单
     * @param id
     * @return
     */
    Menu getById(Integer id);

    /**
     * 更新菜单
     * @param menuDTO
     */
    void update(MenuDTO menuDTO);

    /**
     * 删除菜单
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 批量删除菜单
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> list();

}
