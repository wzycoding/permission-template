package com.wzy.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 分割字符串转化为Long类型
     * @param str 待分割字符串
     * @return 分割结果
     */
    public static List<Long> splitToListLong(String str) {
        List<String> strList = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .splitToList(str);
        return strList.stream().map(Long::parseLong).collect(Collectors.toList());
    }
}
