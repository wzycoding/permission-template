package com.wzy.dao;

import com.wzy.entity.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    @Insert(" <script>" +
            "   insert sys_role" +
            "   <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"id!=null\"> " +
            "       id," +
            "   </if>" +
            "   <if test=\"name!=null\"> " +
            "       name," +
            "   </if>" +
            "   <if test=\"type!=null\"> " +
            "       type," +
            "   </if>" +
            "   <if test=\"seq!=null\"> " +
            "       seq," +
            "   </if>" +
            "   <if test=\"remark!=null\"> " +
            "       remark," +
            "   </if>" +
            "   <if test=\"operator!=null\"> " +
            "       operator," +
            "   </if>" +
            "   <if test=\"operatorIp!=null\"> " +
            "       operator_ip," +
            "   </if>" +
            "   </trim>" +
            " <trim prefix=\" values (\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"id != null\">" +
            "       #{id}, " +
            "   </if>" +
            "   <if test=\"name!=null\"> " +
            "       #{name}," +
            "   </if>" +
            "   <if test=\"type!=null\"> " +
            "       #{type}," +
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
    int insertSelective(SysRole sysRole);


    @Select("select role_id from sys_role_user where user_id = #{userId}")
    List<Long> selectByUserId(long userId);

    @Select(" select * from sys_role where deleted = 0")
    List<SysRole> list();
}
