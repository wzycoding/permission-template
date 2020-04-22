package com.wzy.service.impl;

import com.wzy.dao.SysMenuMapper;
import com.wzy.entity.SysMenu;
import com.wzy.param.SysMenuParam;
import com.wzy.service.ISysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Override
    public void insert(SysMenuParam param) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(param, sysMenu);
        sysMenuMapper.insertSeletive(sysMenu);
    }


    @Resource
    private SysMenuMapper sysMenuMapper;
}
