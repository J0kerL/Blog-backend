package com.blog.controller.admin;

import com.blog.constant.Constant;
import com.blog.dto.UserDTO;
import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import com.blog.properties.JwtProperties;
import com.blog.result.Result;
import com.blog.service.AdminService;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
import com.blog.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    @Resource
    private UserService userService;
    @Resource
    private JwtProperties jwtProperties;

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

    /**
     * 注册
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result<UserVO> register(@RequestBody UserDTO userDTO) {

        UserVO userVO = userService.register(userDTO);
        if (userVO == null) {
            return Result.error(Constant.ALREADY_EXISTS);
        }
        log.info("注册成功：{}", userVO);
        return Result.success(userVO, Constant.SUCCESS_REGISTER);
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);
        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
        log.info("当前登录用户：{}", userLoginVO);
        return Result.success(userLoginVO);
    }
}
