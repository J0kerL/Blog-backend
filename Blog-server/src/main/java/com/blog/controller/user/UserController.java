package com.blog.controller.user;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserRegisterDTO;
import com.blog.entity.User;
import com.blog.properties.JwtProperties;
import com.blog.result.Result;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
import com.blog.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.blog.constant.Constant.*;

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
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 注册
     *
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        // 校验验证码
        String storedCaptcha = redisTemplate.opsForValue().get(CODE_KEY + userRegisterDTO.getEmail());
        if (storedCaptcha == null) {
            return Result.error(CODE_PASS);
        }
        if (!storedCaptcha.equals(userRegisterDTO.getCode())) {
            return Result.error(CODE_ERROR);
        }
        // 验证码正确，继续注册
        UserVO userVO = userService.register(userRegisterDTO);
        if (userVO == null) {
            return Result.error(ALREADY_EXISTS);
        }
        log.info("注册成功：{}", userVO);
        // 注册成功后删除验证码
        redisTemplate.delete(CODE_KEY + userRegisterDTO.getEmail());
        return Result.success(userVO, SUCCESS_REGISTER);
    }

    /**
     * 用户前端登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.login(userLoginDTO);
        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
        log.info("当前登录用户：{}", userLoginVO);
        return Result.success(userLoginVO, SUCCESS_LOGIN);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success(ALREADY_EXIT);
    }

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @GetMapping("/captcha")
    public Result<String> sendCaptcha(@RequestParam String email) {
        String code = userService.sendCaptchaEmail(email);
        return Result.success(code);
    }
}
