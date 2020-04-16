package com.wzy.interceptor;

import com.alibaba.fastjson.JSON;
import com.wzy.common.ErrorEnum;
import com.wzy.common.RequestHolder;
import com.wzy.entity.SysUser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            ErrorEnum.NOT_LOGIN.throwException();
        }
        SysUser sysUser = JSON.parseObject(redisTemplate.opsForValue().get("token"), SysUser.class);
        if (sysUser == null) {
            ErrorEnum.TOKEN_EXPIRED.throwException();
        }
        //保存登录信息
        RequestHolder.add(sysUser);
        //保存request对象
        RequestHolder.add(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        removeThreadLocalInfo();
    }

    /**
     * 移除ThreadLocal的用户信息和request信息
     */
    private void removeThreadLocalInfo() {
        RequestHolder.remove();
    }

    @Resource
    private StringRedisTemplate redisTemplate;
}
