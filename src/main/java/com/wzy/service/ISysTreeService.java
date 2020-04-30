package com.wzy.service;

import com.wzy.vo.AclGeneralVO;
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
     * 生成当前用户菜单树
     * @return 用户菜单树
     */
    List<MenuLevelVO> userMenuTree();

    /**
     * 生成所有菜单树
     * @return 所有菜单项组成的树
     */
    List<MenuLevelVO> allMenuTree();

    /**
     * 根据角色id生成对应的权限树
     * @return 权限树
     */
    List<AclGeneralVO> roleAclTree(long roleId);
}
