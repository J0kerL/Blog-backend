package com.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.blog.dto.MenuDTO;
import com.blog.dto.MenuPageQueryDTO;
import com.blog.entity.Menu;
import com.blog.exception.DeletionNotAllowedException;
import com.blog.mapper.MenuMapper;
import com.blog.result.PageResult;
import com.blog.service.MenuService;
import com.blog.vo.MenuVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.blog.constant.Constant.*;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:25
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 获取菜单
     *
     * @return
     */
    @Override
    public List<MenuVO> getMenu() {
        List<MenuVO> list = menuMapper.getMenu();
        log.info("菜单：{}", list);
        if (!list.isEmpty()) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 分页查询菜单
     *
     * @param menuPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(MenuPageQueryDTO menuPageQueryDTO) {
        log.info("分页查询菜单：{}", menuPageQueryDTO);
        
        PageHelper.startPage(menuPageQueryDTO.getPage(), menuPageQueryDTO.getPageSize());
        Page<Menu> page = menuMapper.pageQuery(menuPageQueryDTO);
        
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增菜单
     *
     * @param menuDTO
     */
    @Override
    public void save(MenuDTO menuDTO) {
        log.info("新增菜单：{}", menuDTO);
        
        // 检查菜单标题是否已存在
        Menu existMenu = menuMapper.getByTitle(menuDTO.getTitle());
        if (existMenu != null) {
            throw new DeletionNotAllowedException("菜单标题已存在");
        }
        
        // 检查路径是否已存在
        existMenu = menuMapper.getByPath(menuDTO.getPath());
        if (existMenu != null) {
            throw new DeletionNotAllowedException("菜单路径已存在");
        }
        
        Menu menu = BeanUtil.copyProperties(menuDTO, Menu.class);
        // 设置默认值
        if (menu.getIsExternal() == null) {
            menu.setIsExternal(0);
        }
        
        menuMapper.insert(menu);
    }

    /**
     * 根据ID查询菜单
     *
     * @param id
     * @return
     */
    @Override
    public Menu getById(Integer id) {
        log.info("根据ID查询菜单：{}", id);
        return menuMapper.getById(id);
    }

    /**
     * 更新菜单
     *
     * @param menuDTO
     */
    @Override
    public void update(MenuDTO menuDTO) {
        log.info("更新菜单：{}", menuDTO);
        
        // 检查菜单是否存在
        Menu existMenu = menuMapper.getById(menuDTO.getId());
        if (existMenu == null) {
            throw new DeletionNotAllowedException("菜单不存在");
        }
        
        // 检查菜单标题是否已被其他菜单使用
        Menu titleMenu = menuMapper.getByTitle(menuDTO.getTitle());
        if (titleMenu != null && !titleMenu.getId().equals(menuDTO.getId())) {
            throw new DeletionNotAllowedException("菜单标题已存在");
        }
        
        // 检查路径是否已被其他菜单使用
        Menu pathMenu = menuMapper.getByPath(menuDTO.getPath());
        if (pathMenu != null && !pathMenu.getId().equals(menuDTO.getId())) {
            throw new DeletionNotAllowedException("菜单路径已存在");
        }
        
        Menu menu = BeanUtil.copyProperties(menuDTO, Menu.class);
        menuMapper.update(menu);
    }

    /**
     * 删除菜单
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        log.info("删除菜单：{}", id);
        
        // 检查菜单是否存在
        Menu menu = menuMapper.getById(id);
        if (menu == null) {
            throw new DeletionNotAllowedException("菜单不存在");
        }
        
        menuMapper.deleteById(id);
    }

    /**
     * 批量删除菜单
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        log.info("批量删除菜单：{}", ids);
        
        if (ids == null || ids.isEmpty()) {
            throw new DeletionNotAllowedException("请选择要删除的菜单");
        }
        
        menuMapper.deleteByIds(ids);
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public List<Menu> list() {
        log.info("查询所有菜单");
        return menuMapper.list();
    }

}
