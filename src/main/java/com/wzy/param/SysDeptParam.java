package com.wzy.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门信息入参
 */
@Data
public class SysDeptParam {
    private Long id;
    /**
     * 部门的名称
     */
    private String name;
    /**
     * 父级部门的id
     */
    private Long parentId = 0L;
    /**
     * 展示的顺序
     */
    private Integer seq;
    /**
     * 部门信息的备注
     */
    private String remark;
}
