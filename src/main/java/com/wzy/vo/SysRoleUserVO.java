package com.wzy.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户与角色关系维护视图类
 */
@Data
public class SysRoleUserVO {
    /**
     * 已选中的用户
     */
    private List<SysUserVO> selectedUser;

    /**
     * 未选中的用户
     */
    private List<SysUserVO> unSelectedUser;
}
