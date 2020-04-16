package com.wzy.dao;

import com.wzy.entity.SysUser;
import com.wzy.param.SysUserParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 系统用户DAO
 */
@Mapper
public interface SysUserMapper {

    @Insert("<script>" +
            " insert into sys_user" +
            " <trim prefix='(' suffix=\")\" suffixOverrides=\",\">" +
            "   <if test='username != null'>username, </if>" +
            "   <if test='telephone != null'>telephone, </if>" +
            "   <if test='salt != null'>salt, </if>" +
            "   <if test='mail != null'>mail, </if>" +
            "   <if test='password != null'>password, </if>" +
            "   <if test='deptId != null'>dept_id, </if>" +
            "   <if test='status != null'>status, </if>" +
            "   <if test='remark != null'>remark, </if>" +
            "   <if test='operator != null'>operator,</if>" +
            "   <if test='operatorTime != null'>operator_time,</if>" +
            "   <if test='operatorIp != null'>operator_ip</if>" +
            " </trim>" +
            " <trim prefix='values(' suffix=')' suffixOverrides=','" +
            "   <if test='username != null'>#{username}, </if>" +
            "   <if test='telephone != null'>#{telephone}, </if>" +
            "   <if test='salt != null'>#{salt}, </if>" +
            "   <if test='mail != null'>#{mail}, </if>" +
            "   <if test='password != null'>#{password}, </if>" +
            "   <if test='deptId != null'>#{deptId}, </if>" +
            "   <if test='status != null'>#{status}, </if>" +
            "   <if test='remark != null'>#{remark}, </if>" +
            "   <if test='operator != null'>#{operator}, </if>" +
            "   <if test='operatorTime != null'>#{operatorTime}, </if>" +
            "   <if test='operatorIp != null'>#{operatorIp}, </if>" +
            " </trim>" +
            "</script>")
    int insertSelective(SysUserParam sysUserParam);

    @Insert(" insert into sys_user(username, telephone, salt, mail, password, dept_id, status, remark, operator, operator_time, operator_ip)" +
            " values(#{username}, #{telephone}, #{salt}, #{mail}, #{password}, #{deptId}, #{status}, #{remark}, #{operator}, #{operatorTime}, #{operatorIp} ) ")
    int insert(SysUserParam sysUserParam);

    @Update("<script>" +
            "   update sys_user" +
            "   <set>" +
            "       <if test='username != null'>username = #{username}, </if>" +
            "       <if test='telephone != null'>telephone = #{telephone}, </if>" +
            "       <if test='mail != null'>mail = #{mail}, </if>" +
            "       <if test='password != null'>password = #{password}, </if>" +
            "       <if test='deptId != null'>dept_id = #{deptId}, </if>" +
            "       <if test='status != null'>status = #{status}, </if>" +
            "       <if test='remark != null'>remark = #{remark}, </if>" +
            "       <if test='operator != null'>operator = #{operator}, </if>" +
            "       operator_time = now()" +
            "       <if test='operatorIp != null'>operator_ip = #{operatorIp}, </if>" +
            "   </set>" +
            "  where id = #{id}" +
            "</script>")
    int update(SysUserParam sysUserParam);

    @Select(" select * from sys_user" +
            " where username = #{username}")
    SysUser selectByUsername(String username);

    @Select(" <script> " +
            "   select count(*) from sys_user" +
            "   where username = #{username} " +
            "   <if test=\"id != null\">" +
            "       and id != #{id}" +
            "   </if>" +
            " </scrip>")
    int countByUserName(String username, Long id);

    @Select(" select * from sys_user where id = #{id}")
    SysUser selectById(Long id);

}
