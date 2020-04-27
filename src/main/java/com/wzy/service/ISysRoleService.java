package com.wzy.service;

import com.wzy.param.SysRoleParam;
import com.wzy.vo.SysRoleVo;

import java.util.List;

public interface ISysRoleService {

    void insert(SysRoleParam param);

    List<SysRoleVo> list();
}
