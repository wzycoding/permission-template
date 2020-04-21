package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysAclParam;
import com.wzy.service.ISysAclService;
import com.wzy.vo.SysAclVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限点Controller
 */
@RestController
@RequestMapping("/sys/acl")
public class SysAclController {

    /**
     * 新增权限点接口
     */
    @PostMapping("/save")
    public Result save(SysAclParam param) {
        sysAclService.save(param);
        return Result.success();
    }

    /**
     * 更新权限点信息
     */
    @PostMapping("/update")
    public Result update(SysAclParam param) {
        sysAclService.update(param);
        return Result.success();
    }

    /**
     * 删除权限点信息
     */
    @PostMapping("/remove/{aclId}")
    public Result remove(@PathVariable("aclId") long aclId) {
        sysAclService.deleteById(aclId);
        return Result.success();
    }

    /**
     * 获取权限点详情
     */
    @GetMapping("/detail/{aclId}")
    public Result detail(@PathVariable("aclId") long aclId) {
        SysAclVo vo = sysAclService.getById(aclId);
        return Result.success(vo);
    }

    /**
     * 获取权限点列表
     */
    @GetMapping("/list")
    public Result list() {
        List<SysAclVo> list = sysAclService.list();
        return Result.success(list);
    }


    /**
     * 引入权限点相关service
     */
    @Resource
    private ISysAclService sysAclService;

}
