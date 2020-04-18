package com.wzy.dao;

import com.wzy.entity.SysAclModule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysAclModuleMapper {

    @Insert(" insert into sys_acl_module(name, parent_id, level, seq, remark, operator, operator_ip)" +
            " value (#{name}, #{parentId}, #{level}, #{seq}, #{remark}, #{operator}, #{operatorIp})")
    int insert(SysAclModule sysAclModule);

    @Insert("<script>" +
            " insert into sys_acl_module " +
            " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"name!=null\"> " +
            "       name," +
            "   </if>" +
            "   <if test=\"parentId!=null\">" +
            "       parent_id, " +
            "   </if>" +
            "   <if test=\"level!=null\">" +
            "       level, " +
            "   </if>" +
            "   <if test=\"seq!=null\">" +
            "       seq, " +
            "   </if>" +
            "   <if test=\"remark!=null\">" +
            "       remark," +
            "   </if>" +
            "   <if test=\"operator!=null\">" +
            "       operator, " +
            "   </if>" +
            "   <if test=\"operatorIp!=null\">" +
            "       operator_ip, " +
            "   </if>" +
            " </trim>" +
            " <trim prefix=\" values (\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"id != null\">" +
            "       #{id}, " +
            "   </if>" +
            "   <if test=\"name!=null\"> " +
            "       #{name}," +
            "   </if>" +
            "   <if test=\"parentId!=null\">" +
            "       #{parentId}, " +
            "   </if>" +
            "   <if test=\"level!=null\">" +
            "       #{level}, " +
            "   </if>" +
            "   <if test=\"seq!=null\">" +
            "       #{seq}, " +
            "   </if>" +
            "   <if test=\"remark!=null\">" +
            "       #{remark}," +
            "   </if>" +
            "   <if test=\"operator!=null\">" +
            "       #{operator}, " +
            "   </if>" +
            "   <if test=\"operatorIp!=null\">" +
            "       #{operatorIp}, " +
            "   </if>" +
            " </trim>" +
            "</script>")
    int insertSelective(SysAclModule sysAclModule);

    @Select(" select * from sys_acl_module where id = #{aclModuleId}")
    SysAclModule get(long aclModuleId);

    @Update("<script>" +
            "   update sys_acl_module" +
            "   <set>" +
            "       <if test=\"name!=null\"> " +
            "           name = #{name}, " +
            "       </if>" +
            "       <if test=\"parentId!=null\">" +
            "           parent_id = #{parentId}, " +
            "       </if>" +
            "       <if test=\"level!=null\">" +
            "           `level` = #{level}, " +
            "       </if>" +
            "       <if test=\"seq!=null\">" +
            "           seq = #{seq}, " +
            "       </if>" +
            "       <if test=\"remark!=null\">" +
            "           remark = #{remark}, " +
            "       </if>" +
            "       <if test=\"operator!=null\">" +
            "           operator = #{operator}, " +
            "       </if>" +
            "       <if test=\"operatorIp!=null\">" +
            "           operator_ip = #{operatorIp}, " +
            "       </if>" +
            "           updated_time = now()," +
            "   </set>" +
            "   where id = #{id}" +
            "</script>")
    void update(SysAclModule sysAclModule);

    @Select("<script> " +
            " select count(*) from sys_acl_module " +
            " where name = #{aclModuleName}" +
            " <if test=\"parentId != null\">" +
            "   and parent_id = #{parentId}" +
            " </if> " +
            //避免更新的时候和自身的权限模块名称冲突
            " <if test=\"id != null\">" +
            "   and id != #{id}" +
            " </if> " +
            "</script>")
    int countByNameAndParentId(Long parentId, String aclModuleName, Long id);


    /**
     * ||为连接符，mysql不支持
     * concat()函数进行连接，表示为xxx.开头的层级
     * @param level
     * @return
     */
    @Select(" select * from sys_acl_module where level like CONCAT(#{level}, '.%')")
    List<SysAclModule> getChildAclModuleListByLevel(String level);


    @Update("<script>" +
            " <foreach collection='sysAclModuleList' item='sysAclModule' separator=';' close=\";\">" +
            "  update sys_acl_module" +
            "  set" +
            "  level = #{sysAclModule.level}," +
            "  updated_time = now()" +
            "  where id = #{sysAclModule.id} " +
            " </foreach>" +
            "</script>")
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);


    @Update(" update sys_acl_module set deleted = 1 where id = #{aclModuleId}")
    void deleteByPrimaryKey(long aclModuleId);

    @Select(" select count(*) from sys_acl_module where parent_id = #{parentId} and deleted = 0")
    int countByParentId(long parentId);

    @Select(" select * from sys_acl_module where deleted = 0")
    List<SysAclModule> list();
}
