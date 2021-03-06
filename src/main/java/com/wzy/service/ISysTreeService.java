package com.wzy.service;

import com.wzy.vo.AclModuleLevelVO;
import com.wzy.vo.DeptLevelVO;
import com.wzy.vo.MenuLevelVO;

import java.util.List;

/**
 * 提供转树服务
 */
public interface ISysTreeService {

    /**
     * 生成部门树
     * @return 部门信息
     */
    List<DeptLevelVO> deptTree();

    /**
     * 生成权限模块树
     * @return 权限模块信息
     */
    List<AclModuleLevelVO> aclModuleTree();


    /**
     * 上次当前用户菜单树
     * @return 用户菜单树
     */
    List<MenuLevelVO> userMenuTree();
}
