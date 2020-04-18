package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysAclModuleParam;
import com.wzy.service.ISysAclModuleService;
import com.wzy.service.ISysTreeService;
import com.wzy.vo.AclModuleLevelVO;
import com.wzy.vo.SysAclModuleVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 权限模块controller
 */
@RestController
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    /**
     * 保存权限模块信息
     */
    @PostMapping("/save")
    public Result save(@RequestBody @Valid SysAclModuleParam param) {
        sysAclModuleService.save(param);
        return Result.success();
    }

    /**
     * 更新权限模块信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody @Valid SysAclModuleParam param) {
        sysAclModuleService.update(param);
        return Result.success();
    }

    /**
     * 删除权限模块信息
     */
    @PostMapping("/remove/{aclModuleId}")
    public Result remove(@PathVariable("aclModuleId") long aclModuleId) {
        sysAclModuleService.remove(aclModuleId);
        return Result.success();
    }

    /**
     * 获取权限模块详情
     */
    @GetMapping("/detail/{aclModuleId}")
    public Result get(@PathVariable("aclModuleId") long aclModuleId) {
        SysAclModuleVO sysAclModuleVO = sysAclModuleService.get(aclModuleId);
        return Result.success(sysAclModuleVO);
    }

    /**
     * 获取权限模块树
     */
    @GetMapping("/tree")
    public Result tree() {
        List<AclModuleLevelVO> aclModuleTree = sysTreeService.aclModuleTree();
        return Result.success(aclModuleTree);
    }


    @Resource
    private ISysAclModuleService sysAclModuleService;

    @Resource
    private ISysTreeService sysTreeService;
}
