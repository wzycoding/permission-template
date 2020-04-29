package com.wzy.service.impl;

import com.google.common.collect.Lists;
import com.wzy.common.RequestHolder;
import com.wzy.dao.*;
import com.wzy.entity.SysAcl;
import com.wzy.entity.SysUser;
import com.wzy.service.SysCoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理核心Service
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Override
    public List<SysAcl> getCurrentUserAclList() {
        SysUser currentUser = RequestHolder.getCurrentUser();
        return getUserAclList(currentUser.getId());
    }

    @Override
    public List<SysAcl> getUserAclList(long userId) {
        List<Long> roleIds = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(roleIds);
    }

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysAclMapper sysAclMapper;
}
