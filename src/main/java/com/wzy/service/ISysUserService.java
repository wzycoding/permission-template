package com.wzy.service;

import com.wzy.param.SysLoginParam;
import com.wzy.param.SysUserParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户service
 */
public interface ISysUserService {

    /**
     * 用户登录方法
     * @param param 登录入参
     * @param response response
     */
    void login(SysLoginParam param, HttpServletResponse response);

    /**
     * 保存用户信息
     * @param param 用户信息入参
     */
    void save(SysUserParam param);

    /**
     * 更新用户信息
     * @param param 用户信息入参
     */
    void update(SysUserParam param);

    /**
     * 用户退出登录
     */
    void logout();

}
