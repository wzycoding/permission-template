package com.wzy.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {

    @Select(" select user_id from sys_role_user where role_id = #{roleId} and deleted = 0")
    List<Long> getUserIdListByRoleId(long roleId);

    @Select(" select role_id from sys_role_user where user_id = #{userId} and deleted = 0")
    List<Long> getRoleIdListByUserId(long userId);

    @Insert("<script>" +
            " <foreach collection='userIds' item='userId' separator=';' close=\";\">" +
            "  insert into sys_role_user (role_id, user_id, operator, operator_ip) " +
            "  values (#{roleId}, #{userId}, #{operator}, #{operatorIp})" +
            " </foreach>" +
            "</script>")
    int batchInsert(long roleId, List<Long> userIds, String operator, String operatorIp);

    @Delete(" delete from sys_role_user where role_id = #{roleId}")
    int removeByRoleId(long roleId);

}
