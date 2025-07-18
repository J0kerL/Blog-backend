package com.blog.controller.user;

import com.blog.context.UserContextHolder;
import com.blog.dto.*;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import com.blog.properties.JwtProperties;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
import com.blog.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.blog.constant.Constant.*;

/**
 * @Author Java小猪
 * @Date 2025/3/21 16:07
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "用户相关接口")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名或邮箱查询用户信息
     *
     * @param account
     * @return
     */
    @Operation(summary = "根据用户名或邮箱查询用户信息")
    @GetMapping("/{account}")
    public Result<UserVO> getByAccount(@PathVariable("account") String account) {
        UserVO userVO = userService.getByAccount(account);
        log.info("查询到的用户为：{}", userVO);
        return Result.success(userVO);
    }

    /**
     * 注册
     *
     * @param userRegisterDTO
     * @return
     */
    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        // 校验验证码
        String storedCaptcha = redisTemplate.opsForValue().get(CODE_KEY + userRegisterDTO.getEmail());
        if (storedCaptcha == null) {
            return Result.error(400, CODE_PASS);
        }
        if (!storedCaptcha.equals(userRegisterDTO.getCode())) {
            return Result.error(400, CODE_ERROR);
        }
        // 验证码正确，继续注册
        UserVO userVO = userService.register(userRegisterDTO);
        if (userVO == null) {
            return Result.error(400, ALREADY_EXISTS);
        }
        log.info("注册成功：{}", userVO);
        // 注册成功后删除验证码
        redisTemplate.delete(CODE_KEY + userRegisterDTO.getEmail());
        return Result.success(SUCCESS_REGISTER, userVO);
    }

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @Operation(summary = "登录")
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
                .email(user.getEmail())
                .token(token)
                .build();
        log.info("当前登录用户：{}", userLoginVO);
        return Result.success(SUCCESS_LOGIN, userLoginVO);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(HttpServletRequest request) {
        try {
            // 优先从ThreadLocal获取用户ID
            Integer currentUserId = UserContextHolder.getCurrentId();
            
            // 如果ThreadLocal为空，尝试从JWT token中解析
            if (currentUserId == null) {
                log.warn("ThreadLocal中用户ID为空，尝试从JWT token解析");
                String token = request.getHeader("Authorization");
                if (token != null) {
                    try {
                        Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
                        currentUserId = (Integer) claims.get(USER_ID);
                        log.info("从JWT token解析到用户ID: {}", currentUserId);
                    } catch (Exception e) {
                        log.error("JWT token解析失败: {}", e.getMessage());
                        return Result.error(401, "Token无效，请重新登录");
                    }
                } else {
                    return Result.error(401, "未提供有效的认证信息");
                }
            }
            
            if (currentUserId == null) {
                return Result.error(401, "用户未登录");
            }
            
            // 根据ID查询用户信息
            User user = userMapper.getById(currentUserId);
            if (user == null) {
                return Result.error(404, "用户不存在");
            }
            
            // 构建UserVO对象
            UserVO userVO = UserVO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .password("******")
                    .status(user.getStatus())
                    .avatar(user.getAvatar())
                    .email(user.getEmail())
                    .sex(user.getSex())
                    .roleId(user.getRoleId())
                    .build();
            
            log.info("成功获取用户信息: {}", user.getUsername());
            return Result.success(userVO);
        } catch (Exception e) {
            log.error("获取用户信息失败: {}", e.getMessage());
            return Result.error(500, "获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        try {
            // 获取token
            String token = request.getHeader(jwtProperties.getTokenName());
            Integer currentUserId = null;
            
            // 从token中解析用户ID（用于日志记录）
            if (token != null) {
                try {
                    Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
                    currentUserId = Integer.valueOf(claims.get(USER_ID).toString());
                } catch (Exception e) {
                    log.warn("解析token失败，无法获取用户ID: {}", e.getMessage());
                }
                
                // 将token加入黑名单（设置过期时间与JWT相同）
                String blacklistKey = "blacklist:logout:token";
                redisTemplate.opsForValue().set(
                        blacklistKey,
                        token,
                        jwtProperties.getTtl(),
                        TimeUnit.MILLISECONDS
                );
                log.info("Token已加入黑名单，key: {}, value: {}", blacklistKey, token);
            }

            // 记录退出日志
            log.info("用户: {} 退出登录", currentUserId);

            return Result.success(ALREADY_EXIT);
        } finally {
            // 确保清理ThreadLocal
            UserContextHolder.removeCurrentId();
        }
    }

    /**
     * 用户分页查询
     *
     * @param userPageQueryDTO
     * @return
     */
    @Operation(summary = "用户分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(UserPageQueryDTO userPageQueryDTO) {
        PageResult pageResult = userService.query(userPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增用户
     *
     * @param addUserDTO
     * @return
     */
    @PostMapping("/add")
    @Operation(summary = "新增用户")
    public Result<String> addUser(@RequestBody AddUserDTO addUserDTO) {
        userService.addUser(addUserDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 修改用户
     *
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "修改用户")
    public Result<String> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 根据id批量删除用户
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    @Operation(summary = "根据id批量删除用户")
    public Result<String> deleteByIds(@RequestParam("ids") String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        userService.deleteByIds(idList);
        return Result.success(OPERATE_SUCCESS);
    }
}
