package com.wzy.dao;

import com.wzy.entity.SysAcl;
import com.wzy.vo.SysAclVO;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 权限点DAO
 */
@Mapper
public interface SysAclMapper {

    @Insert("<script>" +
            " insert into sys_acl" +
            " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"code != null\">code, </if>" +
            "   <if test=\"name != null\">name, </if>" +
            "   <if test=\"aclModuleId != null\">acl_module_id, </if>" +
            "   <if test=\"url != null\">url, </if>" +
            "   <if test=\"type != null\">type, </if>" +
            "   <if test=\"seq != null\">seq, </if>" +
            "   <if test=\"remark != null\">remark, </if>" +
            "   <if test=\"operator != null\">operator,</if>" +
            "   <if test=\"operatorIp != null\">operator_ip</if>" +
            " </trim>" +
            " <trim prefix=\"values(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"code != null\">#{code}, </if>" +
            "   <if test=\"name != null\">#{name}, </if>" +
            "   <if test=\"aclModuleId != null\">#{aclModuleId}, </if>" +
            "   <if test=\"url != null\">#{url}, </if>" +
            "   <if test=\"type != null\">#{type}, </if>" +
            "   <if test=\"seq != null\">#{seq}, </if>" +
            "   <if test=\"remark != null\">#{remark}, </if>" +
            "   <if test=\"operator != null\">#{operator}, </if>" +
            "   <if test=\"operatorIp != null\">#{operatorIp}, </if>" +
            " </trim>" +
            "</script>")
    int insertSelective(SysAcl sysAcl);

    @Insert(" insert into sys_acl(code, name, acl_module_id, url, type, seq, remark, operator, operator_ip)" +
            " values(#{code}, #{name}, #{aclModuleId}, #{url}, #{type}, #{seq}, #{remark}, #{operator}, #{operatorIp} ) ")
    int insert(SysAcl sysAcl);

    @Update("<script>" +
            "   update sys_acl" +
            "   <set>" +
            "       <if test=\"code != null\">code = #{code}, </if>" +
            "       <if test=\"name != null\">name = #{name}, </if>" +
            "       <if test=\"aclModuleId != null\">acl_module_id = #{aclModuleId}, </if>" +
            "       <if test=\"url != null\">url = #{url}, </if>" +
            "       <if test=\"type != null\">type = #{type}, </if>" +
            "       <if test=\"remark != null\">remark = #{remark}, </if>" +
            "       <if test=\"operator != null\">operator = #{operator}, </if>" +
            "       updated_time = now(), " +
            "       <if test=\"operatorIp != null\">operator_ip = #{operatorIp}, </if>" +
            "   </set>" +
            "  where id = #{id}" +
            "</script>")
    int update(SysAcl sysAcl);

    @Select(" select * from sys_acl" +
            " where name = #{aclName}")
    SysAcl selectByAclName(String aclName);

    @Select(" <script> " +
            "   select count(*) from sys_acl" +
            "   where name = #{aclName} " +
            "   <if test=\"aclId != null\">" +
            "       and id != #{aclId}" +
            "   </if>" +
            " </script>")
    int countByAclName(String aclName, Long aclId);

    @Select(" select * from sys_acl where id = #{aclId}")
    SysAcl selectById(long aclId);


    @Select(" <script>" +
            " select count(*) " +
            " from sys_acl" +
            " where " +
            " deleted = 0" +
            " <if test='aclModuleId != null'> and acl_module_id = #{aclModuleId} </if>" +
            " <if test='name != null'> and name like '%' #{name} '%' </if>" +
            " </script>")
    int countList(String name, Long aclModuleId);

    @Update(" update sys_acl set deleted = 1 where id = #{aclId}")
    int deleteById(long aclId);

    @Select("<script>" +
            " select * from sys_acl " +
            " where deleted = 0" +
            " <if test='aclModuleId != null'> and acl_module_id = #{aclModuleId} </if>" +
            " <if test='name != null'> and name like '%' #{name} '%' </if>" +
            " limit #{skip}, #{pageSize}" +
            "</script>")
    List<SysAclVO> list(String name, Long aclModuleId, int skip, int pageSize);

    @Select("<script>" +
            " select * from sys_acl " +
            " where id in " +
            "   <foreach collection='roleIds' separator=',' item='roleId' open='(' close=')'>" +
            "       #{roleId}" +
            "   </foreach>" +
            "</script>")
    List<SysAcl> getByIdList(@Param("roleIds") List<Long> roleIds);


    @Select(" select * from sys_acl where deleted = 0")
    List<SysAcl> listAll();
}
