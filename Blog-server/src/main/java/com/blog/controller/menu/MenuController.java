package com.blog.controller.menu;

import com.blog.dto.MenuDTO;
import com.blog.dto.MenuPageQueryDTO;
import com.blog.entity.Menu;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.MenuService;
import com.blog.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.blog.constant.Constant.OPERATE_SUCCESS;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:23
 */
@RestController
@RequestMapping("/admin/menu")
@Tag(name = "菜单管理接口")
@Slf4j
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

    /**
     * 分页查询菜单
     *
     * @param menuPageQueryDTO
     * @return
     */
    @Operation(summary = "分页查询菜单")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(MenuPageQueryDTO menuPageQueryDTO) {
        log.info("分页查询菜单：{}", menuPageQueryDTO);
        PageResult pageResult = menuService.pageQuery(menuPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增菜单
     *
     * @param menuDTO
     * @return
     */
    @Operation(summary = "新增菜单")
    @PostMapping
    public Result<String> save(@RequestBody @Valid MenuDTO menuDTO) {
        log.info("新增菜单：{}", menuDTO);
        menuService.save(menuDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 根据ID查询菜单
     *
     * @param id
     * @return
     */
    @Operation(summary = "根据ID查询菜单")
    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Integer id) {
        log.info("根据ID查询菜单：{}", id);
        Menu menu = menuService.getById(id);
        return Result.success(menu);
    }

    /**
     * 更新菜单
     *
     * @param menuDTO
     * @return
     */
    @Operation(summary = "更新菜单")
    @PutMapping
    public Result<String> update(@RequestBody @Valid MenuDTO menuDTO) {
        log.info("更新菜单：{}", menuDTO);
        menuService.update(menuDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable Integer id) {
        log.info("删除菜单：{}", id);
        menuService.deleteById(id);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 批量删除菜单
     *
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除菜单")
    @DeleteMapping("/batch")
    public Result<String> deleteByIds(@RequestBody List<Integer> ids) {
        log.info("批量删除菜单：{}", ids);
        menuService.deleteByIds(ids);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Operation(summary = "查询所有菜单")
    @GetMapping("/list")
    public Result<List<Menu>> list() {
        log.info("查询所有菜单");
        List<Menu> menus = menuService.list();
        return Result.success(menus);
    }

}
