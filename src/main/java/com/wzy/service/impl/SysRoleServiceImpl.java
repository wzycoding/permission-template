package com.wzy.service.impl;

import com.google.common.collect.Lists;
import com.wzy.dao.SysRoleMapper;
import com.wzy.entity.SysRole;
import com.wzy.param.SysRoleParam;
import com.wzy.service.ISysRoleService;
import com.wzy.vo.SysRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色相关service
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    @Override
    public void insert(SysRoleParam param) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(param, sysRole);
        sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public List<SysRoleVo> list() {
        List<SysRole> list = sysRoleMapper.list();
        List<SysRoleVo> voList = Lists.newArrayList();
        for (SysRole sysRole : list) {
            SysRoleVo vo = new SysRoleVo();
            vo.setLabel(sysRole.getName());
            BeanUtils.copyProperties(sysRole, vo);
            voList.add(vo);
        }

        return voList;
    }

    @Resource
    private SysRoleMapper sysRoleMapper;
}
