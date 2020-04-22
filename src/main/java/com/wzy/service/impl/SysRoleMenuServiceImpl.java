package com.wzy.service.impl;

import com.wzy.dao.SysRoleMenuMapper;
import com.wzy.param.SysRoleMenuParam;
import com.wzy.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色菜单服务实现类
 */
@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {
    @Override
    public void batchInsert(SysRoleMenuParam param) {
        param.setOperator("admin");
        param.setOperatorIp("127.0.0.1");
        sysRoleMenuMapper.batchInsert(param.getRoleId(), param.getSysMenuIds(), param.getOperator(), param.getOperatorIp());
    }

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
}
