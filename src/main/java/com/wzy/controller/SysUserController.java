package com.wzy.controller;

import com.wzy.common.PageResult;
import com.wzy.common.Result;
import com.wzy.param.SysLoginParam;
import com.wzy.param.SysUserEnableParam;
import com.wzy.param.SysUserParam;
import com.wzy.param.SysUserQueryParam;
import com.wzy.service.ISysUserService;
import com.wzy.vo.SysUserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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
    public Result login(@RequestBody @Valid SysLoginParam param, HttpServletRequest request, HttpServletResponse response) {
        sysUserService.login(param, request, response);
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
     * 用户注销
     */
    @PostMapping("/logout")
    public Result logout() {
        sysUserService.logout();
        return Result.success();
    }

    /**
     * 查询用户信息详情
     */
    @GetMapping("/detail/{userId}")
    public Result detail(@PathVariable("userId") long userId) {
        SysUserVO sysUserVo = sysUserService.getById(userId);
        return Result.success(sysUserVo);
    }

    /**
     * 返回对应部门的用户列表
     */
    @GetMapping("/list")
    public PageResult list(SysUserQueryParam param) {
        List<SysUserVO> userVOList = sysUserService.list(param);
        int total = sysUserService.countListByDeptId(param.getRealName(),param.getDeptId());
        return PageResult.builder().rows(userVOList).total(total).build();
    }

    /**
     * 删除用户信息根据用户id
     */
    @PostMapping("/remove/{userId}")
    public Result remove(@PathVariable("userId") long userId) {
        sysUserService.deleteById(userId);
        return Result.success();
    }

    /**
     * 更新用户可用状态
     */
    @PostMapping("/updateEnable")
    public Result updateEnable(@RequestBody SysUserEnableParam param) {
        sysUserService.updateEnable(param.getUserId(), param.getEnable());
        return Result.success();
    }

    /**
     * 引入用户Service
     */
    @Resource
    private ISysUserService sysUserService;

}
