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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 移除常量导入，因为不再需要角色权限校验
// import static com.blog.constant.Constant.*;

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
        List<MenuVO> list;
        
        try {
            list = menuMapper.getMenu();
        } catch (Exception e) {
            throw new RuntimeException("获取菜单失败: " + e.getMessage());
        }
        // 组装树结构
        return buildMenuTree(list, 0);
    }

    /**
     * 将菜单列表组装为树结构
     * @param menuList 菜单列表
     * @param parentId 父ID
     * @return 树结构菜单
     */
    private List<MenuVO> buildMenuTree(List<MenuVO> menuList, Integer parentId) {
        List<MenuVO> tree = new ArrayList<>();
        for (MenuVO menu : menuList) {
            if (menu.getParentId() != null && menu.getParentId().equals(parentId)) {
                // 递归查找子菜单
                List<MenuVO> children = buildMenuTree(menuList, menu.getId());
                menu.setChildren(children);
                tree.add(menu);
            }
        }
        return tree;
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
        if (menu.getHidden() == null) {
            menu.setHidden(0); // 默认显示
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0); // 默认为根菜单
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
