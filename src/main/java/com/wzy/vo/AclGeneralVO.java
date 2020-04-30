package com.wzy.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 权限点通用VO
 */
@Data
public class AclGeneralVO {
    /**
     * id
     */
    private Long id;
    /**
     * 权限模块名称
     */
    private String label;
    /**
     * 显示的顺序
     */
    private Integer seq;

    /**
     * 是否是权限点节点
     */
    private boolean isAcl;

    /**
     * 是否被选中, 默认未被选中
     */
    private boolean checked = false;

    /**
     * 是否有权限操作：能分配的权限要小于自己的权限
     */
    private boolean hasAcl = false;

    /**
     * 子模块信息
     */
    private List<AclGeneralVO> children = Lists.newArrayList();
}
