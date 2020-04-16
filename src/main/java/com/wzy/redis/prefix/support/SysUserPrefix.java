package com.wzy.redis.prefix.support;

import com.wzy.redis.prefix.BasePrefix;

/**
 * Redis用户前缀实现类
 */
public class SysUserPrefix extends BasePrefix {

    /**
     * token过期时间
     */
    public static final int TOKEN_EXPIRE_SECOND = 60 * 30;
    /**
     * token key 前缀
     */
    public static final String TOKEN_PREFIX = "token";
    /**
     * 调用父类构造函数
     * @param prefix key前缀
     */
    public SysUserPrefix(String prefix) {
        super(prefix);
    }

    /**
     * 调用父类构造函数
     * @param expireSeconds 过期时间
     * @param prefix key前缀
     */
    public SysUserPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SysUserPrefix tokenPrefix = new SysUserPrefix(TOKEN_EXPIRE_SECOND, TOKEN_PREFIX);
}
