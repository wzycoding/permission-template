package com.wzy.dao;

import com.wzy.entity.SysMenu;
import com.wzy.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色与菜单关系表DAO
 */
@Mapper
public interface SysRoleMenuMapper {

    @Insert(" insert into sys_role_menu(role_id, menu_id, operator, operator_ip)" +
            " value(#{roleId}, #{menuId}, #{operator}, #{operatorIp})")
    int insert(SysRoleMenu sysRoleMenu);

    @Insert("<script>" +
            " <foreach collection='sysMenuIds' item='sysMenuId' separator=';' close=\";\">" +
            "  insert into sys_role_menu (role_id, menu_id, operator, operator_ip) " +
            "  values (#{roleId}, #{sysMenuId}, #{operator}, #{operatorIp})" +
            " </foreach>" +
            "</script>")
    int batchInsert(@Param("roleId") long roleId, @Param("sysMenuIds") List<Long> sysMenuIds, String operator, String operatorIp);

}
