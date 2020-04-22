package com.wzy.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 修改日志记录表
 */
@Data
public class SysLog {
    /**
     * id
     */
    private Long id;

    /**
     * 权限更新的类型：1部门，2用户，3权限模块，4模块，5角色，6角色用户关系，7，角色权限关系
     */
    private Integer type;

    /**
     * 基于type后指定的对象id，比如用户、权限、角色表等表的主键
     */
    private Long targetId;

    /**
     * 旧值
     */
    private String oldValue;

    /**
     * 新值
     */
    private String newValue;

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
     * 当前是否复原过，0：没有，1：已经复原
     */
    private Integer status;
}
