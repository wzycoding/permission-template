package com.wzy.param;

import lombok.Data;

@Data
public class SysRoleParam {
    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

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
