package com.wzy.service;

import com.wzy.param.SysLoginParam;
import com.wzy.param.SysUserParam;
import com.wzy.vo.SysUserVO;

import javax.servlet.http.HttpServletRequest;
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
    void login(SysLoginParam param, HttpServletRequest request, HttpServletResponse response);

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

    /**
     * 根据用户id返回用户详情
     * @param userId 用户id
     * @return 用户详情
     */
    SysUserVO getById(long userId);

}
