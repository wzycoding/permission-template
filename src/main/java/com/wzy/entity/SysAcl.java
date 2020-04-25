package com.wzy.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统权限实体类
 */
@Data
@Builder
public class SysAcl {
    /**
     * id
     */
    private Long id;

    /**
     * 权限码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限模块id
     */
    private Long aclModuleId;

    /**
     * 权限url
     */
    private String url;

    /**
     * 类型，1：菜单， 2. 按钮 3. 其他
     */
    private Integer type;

    /**
     * 优先级
     */
    private Integer seq;
    /**
     * 权限备注
     */
    private String remark;

    /**
     * 操作者
     */
    private String operator;
    /**
     * 操作者的ip
     */
    private String operatorIp;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 权限状态：1为正常状态，0为冻结状态
     */
    private Integer enable;

    /**
     * 删除状态：1为已删除，0为未删除
     */
    private Integer deleted;


}
