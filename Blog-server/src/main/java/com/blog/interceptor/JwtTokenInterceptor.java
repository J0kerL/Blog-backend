package com.blog.interceptor;

import com.blog.annotation.RequireLogin;
import com.blog.context.UserContextHolder;
import com.blog.properties.JwtProperties;
import com.blog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

import static com.blog.constant.Constant.*;

/**
 * @Author Java小猪
 * @Date 2025/3/26 10:16
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;
    
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        
        log.info("JWT拦截器拦截请求: {}", request.getRequestURI());

        // 检查方法或类上是否有@RequireLogin注解
        RequireLogin methodAnnotation = method.getAnnotation(RequireLogin.class);
        RequireLogin classAnnotation = clazz.getAnnotation(RequireLogin.class);
        
        boolean requireLogin = false;
        
        // 方法级注解优先于类级注解
        if (methodAnnotation != null) {
            requireLogin = methodAnnotation.value();
        } else if (classAnnotation != null) {
            requireLogin = classAnnotation.value();
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());
        
        // 尝试解析token并设置用户信息（无论是否需要登录都尝试解析）
        Integer userId = null;
        if (token != null && !token.isEmpty()) {
            try {
                //2、检查token是否在黑名单中
                String blacklistKey = "blacklist:logout:token";
                String blacklistedToken = redisTemplate.opsForValue().get(blacklistKey);
                if (token.equals(blacklistedToken)) {
                    log.warn("Token已在黑名单中: {}", token);
                    if (requireLogin) {
                        response.setStatus(401);
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write(TOKEN_ERROR);
                        return false;
                    }
                } else {
                    //3、校验令牌
                    log.info("jwt校验:{}", token);
                    Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
                    userId = Integer.valueOf(claims.get(USER_ID).toString());
                    log.info("当前用户id：{}", userId);
                    // 把当前登录用户的id存入本地线程变量中
                    UserContextHolder.setCurrentId(userId);
                }
            } catch (Exception ex) {
                log.error("token验证失败:{}", ex.getMessage());
                // 如果需要登录但token无效，返回401
                if (requireLogin) {
                    response.setStatus(401);
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(TOKEN_ERROR);
                    return false;
                }
                // 如果不需要登录，token无效也继续执行，但不设置用户信息
            }
        }
        
        // 如果需要登录但没有有效的用户信息，返回401
        if (requireLogin && userId == null) {
            log.warn("接口需要登录但未提供有效token: {}", request.getRequestURI());
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(TOKEN_ERROR);
            return false;
        }
        
        //4、通过，放行
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理ThreadLocal，防止内存泄漏
        UserContextHolder.removeCurrentId();
        log.debug("已清理用户上下文ThreadLocal");
    }
}
