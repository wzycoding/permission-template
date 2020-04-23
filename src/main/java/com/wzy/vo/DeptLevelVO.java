package com.wzy.vo;

import com.google.common.collect.Lists;
import com.wzy.entity.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门层级视图类
 */
@Data
public class DeptLevelVO {


    /**
     * id
     */
    private Long id;
    /**
     * 部门名称
     */
    private String label;
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
    private LocalDateTime updateTime;

    /**
     * 子元素
     */
    private List<DeptLevelVO> children = Lists.newArrayList();

    /**
     * 完成entity->deptLevelVo转化
     * @param sysDept 部门实体对象
     * @return 视图对象
     */
    public static DeptLevelVO convert(SysDept sysDept) {
        DeptLevelVO deptLevelVO = new DeptLevelVO();
        BeanUtils.copyProperties(sysDept, deptLevelVO);
        deptLevelVO.setLabel(sysDept.getName());
        return deptLevelVO;
    }
}
