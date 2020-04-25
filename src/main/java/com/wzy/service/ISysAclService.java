package com.wzy.service;

import com.wzy.param.SysAclParam;
import com.wzy.param.SysAclQueryParam;
import com.wzy.vo.SysAclVo;

import java.util.List;

/**
 * 权限点Service接口
 */
public interface ISysAclService {

    /**
     * 新增权限点信息
     * @param param 入参
     */
    void save(SysAclParam param);

    /**
     * 更新权限点信息
     * @param param 入参
     */
    void update(SysAclParam param);

    /**
     * 获取权限点列表
     * @return 权限点列表
     */
    List<SysAclVo> list(SysAclQueryParam param);

    /**
     * 获取权限点详情
     * @param aclId 权限点id
     * @return 权限点信息
     */
    SysAclVo getById(long aclId);

    /**
     * 删除权限点信息通过id
     * @param aclId 权限点id
     */
    void deleteById(long aclId);

    /**
     * 统计总数
     * @param param 入参
     * @return 总数
     */
    int countList(SysAclQueryParam param);

}
