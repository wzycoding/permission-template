package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysDeptParam;
import com.wzy.service.ISysDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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


    @Resource
    private ISysDeptService sysDeptService;
}
