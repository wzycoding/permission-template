package com.wzy.vo;

import com.google.common.collect.Lists;
import com.wzy.entity.SysAclModule;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class AclModuleLevelVO {


    /**
     * id
     */
    private Long id;
    /**
     * 权限模块名称
     */
    private String label;
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
     * 子模块信息
     */
    private List<AclModuleLevelVO> children = Lists.newArrayList();

    /**
     * 转化对象： SysAclModule->AclModuleLevelVO
     * @param sysAclModule 权限模块对象
     * @return 权限模块视图对象
     */
    public static AclModuleLevelVO convert(SysAclModule sysAclModule) {
        AclModuleLevelVO aclModuleLevelVO = new AclModuleLevelVO();
        BeanUtils.copyProperties(sysAclModule, aclModuleLevelVO);
        aclModuleLevelVO.setLabel(sysAclModule.getName());
        return aclModuleLevelVO;
    }
}
