package com.blog.controller.admin;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.UserPageQueryDTO;
import com.blog.entity.User;
import com.blog.properties.JwtProperties;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.AdminService;
import com.blog.utils.JwtUtil;
import com.blog.vo.MenuVO;
import com.blog.vo.UserLoginVO;
import com.blog.vo.UserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    public Result<UserVO> getByName(@PathVariable("username") String username) {
        UserVO userVO = adminService.getByName(username);
//        if (user == null) {
//            return Result.error(404,USER_NOT_FOUND);
//        }
        log.info("查询到的用户为：{}", userVO);
        return Result.success(userVO);
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
        return Result.success(SUCCESS_LOGIN,userLoginVO);
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
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> pageQuery(@RequestBody UserPageQueryDTO userPageQueryDTO) {
        PageResult pageResult = adminService.query(userPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/menu")
    public Result<List<MenuVO>> getMenu(){
        List<MenuVO> list = adminService.getMenu();
        return Result.success(list);
    }
}
