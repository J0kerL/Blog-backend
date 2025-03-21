package com.blog.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Java小猪
 * @Date 2025/3/21 16:07
 */
@RestController
@RequestMapping("/user/user")
@Tag(name = "用户相关接口")
public class UserController {

    @GetMapping("/test")
    @Operation(summary = "测试")
    public String test() {
        return "test";
    }

}
