package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysMenuParam;
import com.wzy.param.SysRoleMenuParam;
import com.wzy.service.ISysMenuService;
import com.wzy.service.ISysRoleMenuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统菜单controller
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
    @PostMapping("/save")
    public Result save(@RequestBody SysMenuParam param) {
        sysMenuService.insert(param);
        return Result.success();
    }

    @PostMapping("/roleMenu/save")
    public Result saveRoleMenu(@RequestBody SysRoleMenuParam param) {
        sysRoleMenuService.batchInsert(param);
        return Result.success();
    }

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private ISysRoleMenuService sysRoleMenuService;

}
