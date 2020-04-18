package com.wzy.service;

import com.wzy.param.SysAclModuleParam;
import com.wzy.vo.SysAclModuleVO;

/**
 * 权限模块Service接口
 */
public interface ISysAclModuleService {
    /**
     * 新增权限模块
     * @param param 入参
     */
    void save(SysAclModuleParam param);

    /**
     * 更新权限模块
     * @param param 入参
     */
    void update(SysAclModuleParam param);

    /**
     * 逻辑删除权限模块id
     * @param aclModuleId 权限模块id
     */
    void remove(long aclModuleId);

    /**
     * 获取权限模块详情
     * @param aclModuleId 权限模块id
     * @return 权限模块信息
     */
    SysAclModuleVO get(long aclModuleId);
}
