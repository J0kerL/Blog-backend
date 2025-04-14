package com.blog.controller.captcha;

import com.blog.result.Result;
import com.blog.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:11
 */
@RestController
@RequestMapping("/captcha")
@Slf4j
@Tag(name = "验证码相关接口")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @Operation(summary = "发送验证码")
    @GetMapping("/sendCaptcha")
    public Result<String> sendCaptcha(@RequestParam String email) {
        String code = captchaService.sendCaptchaEmail(email);
        return Result.success(code);
    }

}
