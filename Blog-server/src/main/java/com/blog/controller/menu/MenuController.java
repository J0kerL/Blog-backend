package com.blog.controller.menu;

import com.blog.result.Result;
import com.blog.service.MenuService;
import com.blog.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:23
 */
@RestController
@RequestMapping("/menu")
@Tag(name = "菜单相关接口")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 获取菜单
     *
     * @return
     */
    @Operation(summary = "获取菜单")
    @GetMapping("/getMenu")
    public Result<List<MenuVO>> getMenu() {
        List<MenuVO> list = menuService.getMenu();
        return Result.success(list);
    }

}
