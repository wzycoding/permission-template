package com.wzy.dao;

import com.wzy.entity.SysMenu;
import com.wzy.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

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

    @Select("<script>" +
            " select menu_id from sys_role_menu where role_id in " +
            " <foreach collection='roleIdList' item='roleId' separator=',' open='(' close=\")\">" +
            "   #{roleId}" +
            " </foreach>" +
            "</script>")
    List<Long> selectByRoleIdList(@Param("roleIdList") List<Long> roleIdList);
}
