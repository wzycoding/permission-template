package com.wzy.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录入参
 */
@Data
public class SysLoginParam {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
