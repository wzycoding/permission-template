package com.wzy.redis.prefix;

/**
 * Redis key 前缀
 */
public interface KeyPrefix {
    /**
     * 获取Redis Key前缀
     * @return key前缀
     */
    String getPrefix();

    /**
     * 获取过期时间
     * @return
     */
    int expireSeconds();
}
