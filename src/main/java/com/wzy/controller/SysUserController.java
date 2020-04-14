package com.wzy.controller;

import com.wzy.common.Result;
import com.wzy.param.SysLoginParam;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户登录Controller
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @PostMapping("/login")
    public Result login(@RequestBody @Valid SysLoginParam param) {

        return null;
    }

    @Resource
    StringRedisTemplate redisTemplate;
}
