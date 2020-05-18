package com.wzy.service;

import com.wzy.entity.SysMenu;
import com.wzy.param.SysMenuParam;

public interface ISysMenuService {
    void insert(SysMenuParam param);

    void delete(long menuId);

    void update(SysMenuParam param);

    SysMenu getById(long menuId);

}
