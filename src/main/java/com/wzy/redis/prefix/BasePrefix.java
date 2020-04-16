package com.wzy.redis.prefix;

/**
 * Redis key前缀基类
 */
public abstract class BasePrefix implements KeyPrefix{
    /**
     * key的过期时间
     */
    private int expireSeconds;

    /**
     * key的前缀
     */
    private String prefix;

    /**
     * 获取key前缀，将类的名称与prefix拼接起来
     * @return  实际key前缀
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }

    /**
     * 获取过期时间
     * @return 过期时间（秒）
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 前缀的构造方法
     * @param prefix 前缀
     */
    public BasePrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 前缀和过期时间的
     * @param expireSeconds 过期时间
     * @param prefix 前缀
     */
    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }
}
