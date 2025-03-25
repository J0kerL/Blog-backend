package com.blog.controller.user;

import com.blog.constant.Constant;
import com.blog.dto.UserDTO;
import com.blog.result.Result;
import com.blog.service.UserService;
import com.blog.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Java小猪
 * @Date 2025/3/21 16:07
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户相关接口")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

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

    // TODO 登录
}
