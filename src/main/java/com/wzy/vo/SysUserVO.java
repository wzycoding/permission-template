package com.wzy.vo;

import lombok.Data;

@Data
public class SysUserVO {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 邮箱地址
     */
    private String mail;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户备注
     */

    private String remark;

    /**
     * 是否可用
     */
    private boolean enable;

}
