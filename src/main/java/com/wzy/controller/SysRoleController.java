package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysRoleParam;
import com.wzy.service.ISysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色相关controller
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    /**
     * 保存角色信息
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysRoleParam param){
        sysRoleService.insert(param);
        return Result.success();
    }

    /**
     * 返回角色列表
     */
    @GetMapping("/list")
    public Result list() {
        return Result.success(sysRoleService.list());
    }

    @Resource
    private ISysRoleService sysRoleService;
}
