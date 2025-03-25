package com.blog.controller.admin;

import com.blog.constant.Constant;
import com.blog.entity.User;
import com.blog.result.Result;
import com.blog.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Java小猪
 * @Date 2025/3/24 16:06
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "管理员相关接口")
@Slf4j
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
            return Result.error(Constant.USER_NOT_FOUND);
        }
        log.info("查询到的用户为：{}", user);
        return Result.success(user);
    }

}
