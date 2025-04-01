package com.blog.controller.admin;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserPageQueryDTO;
import com.blog.entity.User;
import com.blog.properties.JwtProperties;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.AdminService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.blog.constant.Constant.*;

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
            return Result.error(USER_NOT_FOUND);
        }
        log.info("查询到的用户为：{}", user);
        return Result.success(user);
    }

    /**
     * 管理后端登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        User user = adminService.login(userLoginDTO);
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
     *员工分页查询
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> pageQuery(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        PageResult pageResult = adminService.query(userPageQueryDTO);
        return Result.success(pageResult);
    }
}
