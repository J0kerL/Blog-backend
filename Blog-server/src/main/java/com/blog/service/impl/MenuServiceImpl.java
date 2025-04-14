package com.blog.service.impl;

import com.blog.mapper.MenuMapper;
import com.blog.service.MenuService;
import com.blog.vo.MenuVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
