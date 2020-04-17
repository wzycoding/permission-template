package com.wzy.service;

import com.wzy.param.SysDeptParam;

/**
 * 系统部门service
 */
public interface ISysDeptService {
    /**
     * 保存部门信息
     * @param sysDeptParam 入参
     */
    void save(SysDeptParam sysDeptParam);

    /**
     * 删除部门信息
     * @param deptId 部门id
     */
    void remove(long deptId);

    /**
     * 更新部门信息
     * @param param 入参
     */
    void update(SysDeptParam param);
}
