package com.wzy.vo;

import lombok.Data;

@Data
public class SysRoleVo {
    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String label;

    /**
     * 角色类型：1：管理员角色 2：其他
     */
    private Integer type;

    /**
     * 优先级
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;
}
