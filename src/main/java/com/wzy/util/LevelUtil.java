package com.wzy.util;


import org.apache.commons.lang3.StringUtils;

/**
 * 层级工具类
 */
public class LevelUtil {
    /**
     * 层级的分隔符为.
     */
    public static final String SEPARATOR = ".";

    /**
     * 根节点的层级
     */
    public static final String ROOT = "0";

    /**
     * 计算层级:0
     *         0.1
     *         0.1.2
     *         0.2.1
     * @param parentLevel 父节点的层级
     * @param parentId 父节点的id
     * @return 计算层级结果
     */
    public static String calculateLevel(String parentLevel, long parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            //进行字符串连接得出层级
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
