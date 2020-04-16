package com.wzy.util;

import java.util.UUID;

/**
 * 获取UUID的方法
 */
public class UUIDUtil {
    /**
     * 将UUID中的横线替换成空的字符串
     * @return 处理后的UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
