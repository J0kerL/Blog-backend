package com.blog.controller.admin;

import com.blog.dto.RolePageQueryDTO;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Java小猪
 * @Date 2025/4/3 15:51
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色分页查询
     *
     * @param rolePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(RolePageQueryDTO rolePageQueryDTO) {
        PageResult pageResult = roleService.page(rolePageQueryDTO);
        return Result.success(pageResult);
    }

}
