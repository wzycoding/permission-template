package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysDeptParam;
import com.wzy.service.ISysDeptService;
import com.wzy.service.ISysTreeService;
import com.wzy.vo.DeptLevelVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 部门controller
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    /**
     * 保存部门信息
     */
    @PostMapping("/save")
    public Result saveDept(@RequestBody @Valid SysDeptParam param) {
        sysDeptService.save(param);
        return Result.success();
    }

    /**
     * 更新部门信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody @Valid SysDeptParam param) {
        sysDeptService.update(param);
        return Result.success();
    }

    /**
     * 删除部门信息
     */
    @PostMapping("/remove/{deptId}")
    public Result remove(@PathVariable("deptId") long deptId) {
        sysDeptService.remove(deptId);
        return Result.success();
    }

    /**
     * 生成部门树
     */
    @GetMapping("/tree")
    public Result tree() {
        List<DeptLevelVO> deptLevelVOS = treeService.deptTree();
        return Result.success(deptLevelVOS);
    }

    @Resource
    ISysTreeService treeService;
    @Resource
    private ISysDeptService sysDeptService;
}
