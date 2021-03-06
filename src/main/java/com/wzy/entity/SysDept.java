package com.wzy.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统部门表实体类
 */
@Data
@Builder
public class SysDept {
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
     * 部门状态：1为正常状态，0为冻结状态
     */
    private Integer enable;

    /**
     * 删除状态：1为已删除，0为未删除
     */
    private Integer deleted;

}
