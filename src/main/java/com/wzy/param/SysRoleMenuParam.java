package com.wzy.param;

import lombok.Data;

import java.util.List;

@Data
public class SysRoleMenuParam {
    private Long roleId;
    private List<Long> sysMenuIds;
    private String operator;
    private String operatorIp;
}
