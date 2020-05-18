package com.wzy.service.impl;

import com.wzy.dao.SysMenuMapper;
import com.wzy.entity.SysMenu;
import com.wzy.param.SysMenuParam;
import com.wzy.service.ISysMenuService;
import com.wzy.util.LevelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Override
    public void insert(SysMenuParam param) {

        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(param, sysMenu);
        long parentId = param.getParentId();
        String parentIdLevel = getLevel(parentId);

        sysMenu.setLevel(LevelUtil.calculateLevel(parentIdLevel, parentId));

        sysMenuMapper.insertSelective(sysMenu);
    }

    /**
     * 获取菜单的level
     * @param menuId 菜单id
     * @return 菜单level
     */
    private String getLevel(long menuId) {
        SysMenu sysMenu = sysMenuMapper.getById(menuId);
        if (sysMenu == null) {
            return null;
        }
        return sysMenu.getLevel();
    }

    @Override
    public void delete(long menuId) {
        sysMenuMapper.deleteById(menuId);
    }

    @Override
    public void update(SysMenuParam param) {
        sysMenuMapper.update(param);
    }

    @Override
    public SysMenu getById(long menuId) {
        return sysMenuMapper.getById(menuId);
    }


    @Resource
    private SysMenuMapper sysMenuMapper;
}
