package com.wzy.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleAclMapper {

    @Insert("<script>" +
            " <foreach collection='aclIds' item='aclId' separator=';' close=\";\">" +
            "  insert into sys_role_acl (role_id, acl_id, operator, operator_ip) " +
            "  values (#{roleId}, #{aclId}, #{operator}, #{operatorIp})" +
            " </foreach>" +
            "</script>")
    int batchInsert(long roleId, List<Long> aclIds, String operator, String operatorIp);

    @Select(" select acl_id from sys_role_acl where role_id = #{roleId} and deleted = 0")
    List<Long> getAclIdListByRoleId(long roleId);

    @Delete(" delete from sys_role_acl where role_id = #{roleId}")
    int removeByRoleId(long roleId);
}
