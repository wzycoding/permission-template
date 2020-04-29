package com.wzy.service;

import com.wzy.entity.SysAcl;

import java.util.List;

public interface SysCoreService {
    /**
     * 获取当前用户的权限列表
     * @return 权限列表
     */
    List<SysAcl> getCurrentUserAclList();

    /**
     * 获取用户权限列表
     * @param userId 用户id
     * @return 权限列表
     */
    List<SysAcl> getUserAclList(long userId);

}
