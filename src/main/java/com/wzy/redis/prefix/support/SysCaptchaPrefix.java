package com.wzy.redis.prefix.support;

import com.wzy.redis.prefix.BasePrefix;

/**
 * 验证码redis前缀
 */
public class SysCaptchaPrefix extends BasePrefix {

    public static String CAPTCHA_PREFIX = "captcha";

    public static int EXPIRE_SECONDS = 60 * 5;

    public SysCaptchaPrefix(String prefix) {
        super(prefix);
    }

    public SysCaptchaPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SysUserPrefix captcha = new SysUserPrefix(EXPIRE_SECONDS, CAPTCHA_PREFIX);
}
