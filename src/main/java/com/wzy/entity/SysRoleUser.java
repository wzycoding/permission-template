package com.wzy.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色用户关联表
 */
@Data
public class SysRoleUser {
    /**
     * id
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;

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
