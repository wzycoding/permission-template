package com.wzy.vo;

import lombok.Data;

/**
 * 权限点视图类
 */
@Data
public class SysAclVo {
    /**
     * id
     */
    private Long id;

    /**
     * 权限码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限模块id
     */
    private Long aclModuleId;

    /**
     * 权限url
     */
    private String url;

    /**
     * 类型，1：菜单， 2. 按钮 3. 其他
     */
    private Integer type;

    /**
     * 优先级
     */
    private Integer seq;
    /**
     * 权限备注
     */
    private String remark;
}
