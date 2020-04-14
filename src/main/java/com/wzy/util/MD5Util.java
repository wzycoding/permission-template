package com.wzy.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具类
 */
public class MD5Util {
    /**
     * 盐值
     */
    private static final String salt = "1a2b3c4d";

    /**
     * MD5加密
     * @param src 加密前字符串
     * @return 加密后结果
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
    /**
     * 从用户输入的明文转化为存到数据库中的密文
     * @param inputPass 输入的字符串
     * @return 加密后的结果
     */
    public static String inputPassToDBPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 主方法测试
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456"));
    }
}
