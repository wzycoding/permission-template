package com.wzy.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * MD5工具类
 */
public class MD5Util {
    private static char[] chars = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'
    };
    /**
     * 盐值
     */
    private static final String salt = "1a2b3c4d";
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    public static String formPassToDBPass(String inputPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDB(String input, String saltDB) {
        String formPass = inputPassFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    /**
     * 生成数据库盐值
     * @param length 要生成盐值的位数
     * @return 数据盐值
     */
    public static String generateDbSlat(int length) {
        Random random = new Random();
        StringBuilder dbSlat = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            dbSlat.append(chars[random.nextInt(chars.length)]);
        }
        return dbSlat.toString();
    }

    /**
     * 主方法测试
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(generateDbSlat(8));
    }
}
