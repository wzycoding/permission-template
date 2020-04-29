package com.wzy.service;

import com.wzy.param.SysRoleParam;
import com.wzy.vo.SysRoleUserVO;
import com.wzy.vo.SysRoleVo;

import java.util.List;

/**
 * 角色服务接口
 */
public interface ISysRoleService {

    /**
     * 保存角色信息
     * @param param 角色入参
     */
    void insert(SysRoleParam param);

    /**
     * 获取所有角色列表
     * @return 角色列表
     */
    List<SysRoleVo> list();

    /**
     * 获取用户列表通过角色ID
     * @param roleId 角色id
     * @return 角色视图类
     */
    SysRoleUserVO getUserListByRoleId(long roleId);

    /**
     * 保存角色与用户关系
     * @param roleId 角色id
     * @param userIds 用户id列表
     */
    void changeUsers(long roleId, String userIds);

    /**
     * 保存角色与权限点关系
     * @param roleId
     * @param aclIds
     */
    void changeAcls(long roleId, String aclIds);
}
