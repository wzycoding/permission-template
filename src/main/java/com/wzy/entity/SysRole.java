package com.wzy.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表实体类
 */
@Data
public class SysRole {
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
    private LocalDateTime updatedTime;

    /**
     * 操作者的ip
     */
    private String operatorIp;

    /**
     * 可用状态：1为正常状态，0为冻结状态
     */
    private Integer enable;

    /**
     * 删除状态：1为已删除，0为未删除
     */
    private Integer deleted;
}
