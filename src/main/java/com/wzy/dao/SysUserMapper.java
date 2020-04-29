package com.wzy.dao;

import com.wzy.entity.SysUser;
import com.wzy.param.SysUserParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 系统用户DAO
 */
@Mapper
public interface SysUserMapper {

    @Insert("<script>" +
            " insert into sys_user" +
            " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"username != null\">username, </if>" +
            "   <if test=\"realName != null\">real_name, </if>" +
            "   <if test=\"telephone != null\">telephone, </if>" +
            "   <if test=\"salt != null\">salt, </if>" +
            "   <if test=\"mail != null\">mail, </if>" +
            "   <if test=\"password != null\">password, </if>" +
            "   <if test=\"deptId != null\">dept_id, </if>" +
            "   <if test=\"remark != null\">remark, </if>" +
            "   <if test=\"operator != null\">operator,</if>" +
            "   <if test=\"operatorIp != null\">operator_ip</if>" +
            " </trim>" +
            " <trim prefix=\"values(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"username != null\">#{username}, </if>" +
            "   <if test=\"realName != null\">#{realName}, </if>" +
            "   <if test=\"telephone != null\">#{telephone}, </if>" +
            "   <if test=\"salt != null\">#{salt}, </if>" +
            "   <if test=\"mail != null\">#{mail}, </if>" +
            "   <if test=\"password != null\">#{password}, </if>" +
            "   <if test=\"deptId != null\">#{deptId}, </if>" +
            "   <if test=\"remark != null\">#{remark}, </if>" +
            "   <if test=\"operator != null\">#{operator}, </if>" +
            "   <if test=\"operatorIp != null\">#{operatorIp}, </if>" +
            " </trim>" +
            "</script>")
    int insertSelective(SysUser sysUser);

    @Insert(" insert into sys_user(username, telephone, salt, mail, password, dept_id, remark, operator, operator_ip)" +
            " values(#{username}, #{telephone}, #{salt}, #{mail}, #{password}, #{deptId}, #{remark}, #{operator}, #{operatorIp} ) ")
    int insert(SysUserParam sysUserParam);

    @Update("<script>" +
            "   update sys_user" +
            "   <set>" +
            "       <if test=\"username != null\">username = #{username}, </if>" +
            "       <if test=\"telephone != null\">telephone = #{telephone}, </if>" +
            "       <if test=\"mail != null\">mail = #{mail}, </if>" +
            "       <if test=\"password != null\">password = #{password}, </if>" +
            "       <if test=\"deptId != null\">dept_id = #{deptId}, </if>" +
            "       <if test=\"remark != null\">remark = #{remark}, </if>" +
            "       <if test=\"operator != null\">operator = #{operator}, </if>" +
            "       updated_time = now(), " +
            "       <if test=\"operatorIp != null\">operator_ip = #{operatorIp}, </if>" +
            "   </set>" +
            "  where id = #{id}" +
            "</script>")
    int update(SysUser sysUser);

    @Select(" select * from sys_user" +
            " where username = #{username}")
    SysUser selectByUsername(String username);

    @Select(" <script> " +
            "   select count(*) from sys_user" +
            "   where username = #{username} " +
            "   <if test=\"id != null\">" +
            "       and id != #{id}" +
            "   </if>" +
            " </script>")
    int countByUserName(String username, Long id);

    @Select(" select * from sys_user where id = #{id}")
    SysUser selectById(long id);


    @Select(" select count(*) from sys_user where dept_id = #{deptId} and deleted = 0")
    int countByDeptId(long deptId);

    @Select("<script> " +
            "   select * from sys_user " +
            "   where dept_id = #{deptId} and deleted = 0" +
            "   <if test='realName != null'>" +
            "       and real_name like '%' #{realName} '%'" +
            "   </if>" +
            "  limit #{skip}, #{pageSize}" +
            "</script>")
    List<SysUser> list(String realName, long deptId, int pageSize, int skip);

    @Select("<script> " +
            "   select count(*) " +
            "   from sys_user " +
            "   where dept_id = #{deptId} and deleted = 0" +
            "   <if test='realName != null'>" +
            "       and real_name like '%' #{realName} '%'" +
            "   </if>" +
            "</script>")
    int countListByDeptId(String realName, long deptId);

    @Update(" update sys_user set deleted = 1 where id = #{userId}")
    int deleteById(long userId);

    @Update(" update sys_user set enable = #{enable} where id = #{userId}")
    int updateEnable(long userId, int enable);

    @Select(" select * from sys_user where deleted = 0")
    List<SysUser> getAll();

}
