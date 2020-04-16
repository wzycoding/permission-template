package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysLoginParam;
import com.wzy.param.SysUserParam;
import com.wzy.service.ISysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 用户登录Controller
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Valid SysLoginParam param, HttpServletResponse response) {
        sysUserService.login(param, response);
        return Result.success();
    }

    /**
     * 保存用户信息
     */
    @PostMapping("/save")
    public Result save (@RequestBody @Valid SysUserParam param) {
        sysUserService.save(param);
        return Result.success();
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody @Valid SysUserParam param) {
        sysUserService.update(param);
        return Result.success();
    }

    /**
     * 引入用户Service
     */
    @Resource
    private ISysUserService sysUserService;

}
