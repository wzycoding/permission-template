package com.wzy.common;

import com.wzy.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用ThreadLocal保存用户信息、请求信息
 */
public class RequestHolder {

    /**
     * 保存用户信息
     */
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    /**
     * 保存request信息
     */
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    /**
     * 保存token信息
     */
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    /**
     * 添加用户信息到ThreadLocal
     * @param sysUser 用户信息
     */
    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    /**
     * 添加请求信息到ThreadLocal
     * @param request 请求信息
     */
    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    /**
     * 添加token信息到ThreadLocal
     * @param token token信息
     */
    public static void add(String token) {
        tokenHolder.set(token);
    }
    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    /**
     * 获取当前请求信息
     * @return 请求信息
     */
    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    /**
     * 获取当前token
     */
    public static String getCurrentToken() {
        return tokenHolder.get();
    }

    /**
     * 移除当前线程保存的信息
     */
    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
        tokenHolder.remove();
    }


}
