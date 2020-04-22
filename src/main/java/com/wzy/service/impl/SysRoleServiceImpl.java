package com.wzy.service.impl;

import com.wzy.dao.SysRoleMapper;
import com.wzy.entity.SysRole;
import com.wzy.param.SysRoleParam;
import com.wzy.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Override
    public void insert(SysRoleParam param) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(param, sysRole);
        sysRoleMapper.insertSelective(sysRole);
    }

    @Resource
    private SysRoleMapper sysRoleMapper;
}
