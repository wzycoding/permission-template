package com.wzy.param;

import lombok.Data;

/**
 * 菜单信息入参
 */
@Data
public class SysMenuParam {

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 统一层级下的优先级
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 层级
     */
    private String level;
}
