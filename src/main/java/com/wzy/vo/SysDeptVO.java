package com.wzy.vo;

import lombok.Data;

@Data
public class SysDeptVO {
    /**
     * id
     */
    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 上级部门的id
     */
    private Long parentId;
    /**
     * 部门层级
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
