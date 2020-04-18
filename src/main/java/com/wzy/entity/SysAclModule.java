package com.wzy.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统权限模块实体类
 */
@Data
@Builder
public class SysAclModule {

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
     * 权限模块状态：1为正常状态，0为冻结状态
     */
    private Integer enable;

    /**
     * 删除状态：1为已删除，0为未删除
     */
    private Integer deleted;
}
