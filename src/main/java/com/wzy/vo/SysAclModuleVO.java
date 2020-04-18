package com.wzy.vo;

import lombok.Data;

@Data
public class SysAclModuleVO {
    /**
     * id
     */
    private Long id;
    /**
     * 权限模块名称
     */
    private String name;
    /**
     * 上级权限模块的id
     */
    private Long parentId;
    /**
     * 权限模块层级
     */
    private String level;
    /**
     * 显示的顺序
     */
    private Integer seq;
    /**
     * 备注
     */
    private String remark;
}
