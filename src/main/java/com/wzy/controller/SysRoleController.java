package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SaveRoleUserParam;
import com.wzy.param.SysRoleParam;
import com.wzy.service.ISysRoleService;
import com.wzy.service.ISysTreeService;
import com.wzy.vo.AclModuleLevelVO;
import com.wzy.vo.SysRoleUserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 获取用户列表通过角色id
     */
    @GetMapping("/userList/{roleId}")
    public Result<SysRoleUserVO> getUserListByRoleId(@PathVariable("roleId") long roleId) {
        SysRoleUserVO vo = sysRoleService.getUserListByRoleId(roleId);
        return Result.success(vo);
    }

    /**
     * 保存用户与角色关系
     */
    @PostMapping("/changeUsers/{roleId}")
    public Result changeUsers(@PathVariable("roleId") long roleId, @RequestBody SaveRoleUserParam param) {
        sysRoleService.changeUsers(roleId, param.getUserIds());
        return Result.success();
    }

    /**
     * 显示角色对应的权限树
     */
    @GetMapping("/roleAclTree/{roleId}")
    public Result<AclModuleLevelVO> roleAclTree(@PathVariable("roleId") long roleId) {
        List<AclModuleLevelVO> roleAclTree = sysTreeService.roleAclTree(roleId);
        return Result.success(roleAclTree);
    }

    @PostMapping("/changeAcls/{roleId}")
    public Result changeAcls(@PathVariable("roleId") long roleId, @RequestBody String aclIds) {
        sysRoleService.changeAcls(roleId, aclIds);
        return Result.success();
    }

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysTreeService sysTreeService;
}
