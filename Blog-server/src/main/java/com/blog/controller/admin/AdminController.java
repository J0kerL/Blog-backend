package com.blog.controller.admin;

import com.blog.constant.Constant;
import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import com.blog.properties.JwtProperties;
import com.blog.result.Result;
import com.blog.service.AdminService;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
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
    public Result<User> getByName(@PathVariable("username") String username) {
        User user = adminService.getByName(username);
        if (user == null) {
            return Result.error(Constant.USER_NOT_FOUND);
        }
        log.info("查询到的用户为：{}", user);
        return Result.success(user);
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
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

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("已退出登录");
    }
}
