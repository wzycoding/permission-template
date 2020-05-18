package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysMenuParam;
import com.wzy.param.SysRoleMenuParam;
import com.wzy.service.ISysMenuService;
import com.wzy.service.ISysRoleMenuService;
import com.wzy.service.ISysTreeService;
import com.wzy.vo.MenuLevelVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/delete")
    public Result delete(@RequestParam long menuId) {
        sysMenuService.delete(menuId);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody SysMenuParam param) {
        sysMenuService.update(param);
        return Result.success();
    }

    @PostMapping("/roleMenu/save")
    public Result saveRoleMenu(@RequestBody SysRoleMenuParam param) {
        sysRoleMenuService.batchInsert(param);
        return Result.success();
    }

    @GetMapping("/userMenuTree")
    public Result userMenuTree() {
        List<MenuLevelVO> menuLevelVOList = sysTreeService.userMenuTree();
        return Result.success(menuLevelVOList);
    }

    @GetMapping("/allMenuTree")
    public Result allMenuTree() {
        List<MenuLevelVO> menuLevelVOList = sysTreeService.allMenuTree();
        return Result.success(menuLevelVOList);
    }

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private ISysRoleMenuService sysRoleMenuService;

    @Resource
    private ISysTreeService sysTreeService;

}
