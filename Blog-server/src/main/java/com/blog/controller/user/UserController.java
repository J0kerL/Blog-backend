package com.blog.controller.user;

import com.blog.constant.Constant;
import com.blog.dto.UserDTO;
import com.blog.dto.UserLoginDTO;
import com.blog.entity.User;
import com.blog.properties.JwtProperties;
import com.blog.result.Result;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
import com.blog.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Java小猪
 * @Date 2025/3/21 16:07
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private JwtProperties jwtProperties;

    /**
     * 注册
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
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
        return Result.success(userLoginVO, Constant.SUCCESS_LOGIN);
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
