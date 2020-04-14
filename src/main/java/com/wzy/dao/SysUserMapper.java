package com.wzy.dao;

import com.wzy.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    int insert(SysUser sysUser);
}
