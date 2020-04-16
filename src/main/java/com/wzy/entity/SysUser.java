package com.wzy.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统用户实体类: 用户->部门
 */
@Data
@NoArgsConstructor
public class SysUser {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 邮箱地址
     */
    private String mail;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户状态：1为正常状态，0为冻结状态，2为删除状态
     */
    private Integer status;

    /**
     * 用户备注
     */

    private String remark;

    /**
     * 操作者
     */
    private String operator;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 操作者的ip
     */
    private String operatorIp;
}
