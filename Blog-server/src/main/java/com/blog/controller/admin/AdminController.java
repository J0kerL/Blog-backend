package com.blog.controller.admin;

import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import com.blog.result.Result;
import com.blog.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:06
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "管理员相关接口")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    @Operation(summary = "根据用户名查询用户信息")
    public Result<User> getByName(@PathVariable("username") String username) {
        User user = adminService.getByName(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        return Result.success(user);
    }

    /**
     * 注册
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result<UserLoginDTO> register(@RequestBody UserLoginDTO userLoginDTO) {
        UserLoginDTO newAdminLoginDTO = adminService.register(userLoginDTO);
        if (newAdminLoginDTO == null) {
            return Result.error("用户已存在");
        }
        return Result.success(newAdminLoginDTO);
    }
}
