package com.wzy.dao;

import com.wzy.entity.SysDept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysDeptMapper {
    @Insert(" insert into sys_dept(name, parent_id, level, seq, remark, operator, operator_ip)" +
            " value (#{name}, #{parentId}, #{level}, #{seq}, #{remark}, #{operator}, #{operatorIp})")
    int insert(SysDept sysDept);

    @Insert("<script>" +
            " insert into sys_dept " +
            " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"id != null\">" +
            "       id, " +
            "   </if> " +
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
    void insertSelective(SysDept sysDept);

    @Select(" select * from sys_dept where id = #{deptId}")
    SysDept get(long deptId);

    @Select("<script> " +
            " select count(*) from sys_dept " +
            " where name = #{deptName}" +
            " <if test=\"parentId != null\">" +
            "   and parent_id = #{parentId}" +
            " </if> " +
            //避免更新的时候和自身的部门名称冲突
            " <if test=\"id != null\">" +
            "   and id != #{id}" +
            " </if> " +
            "</script>")
    int countByNameAndParentId(Long parentId, String deptName, Long id);
}
