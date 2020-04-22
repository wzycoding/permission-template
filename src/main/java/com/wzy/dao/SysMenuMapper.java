package com.wzy.dao;

import com.wzy.entity.SysMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysMenuMapper {

    @Insert(" insert into sys_menu (name, url, seq, remark, parentId, level, operator, operator_ip)" +
            " values(#{name}, #{url}, #{seq}, #{remark}, #{parentId}, #{level}, #{operator}, #{operatorIp})")
    int insert(SysMenu sysMenu);

    @Insert(" <script>" +
            "   insert into sys_menu" +
            "   <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"id!=null\"> " +
            "       id," +
            "   </if>" +
            "   <if test=\"name!=null\"> " +
            "       name," +
            "   </if>" +
            "   <if test=\"url!=null\"> " +
            "       url," +
            "   </if>" +
            "   <if test=\"seq!=null\"> " +
            "       seq," +
            "   </if>" +
            "   <if test=\"remark!=null\"> " +
            "       remark," +
            "   </if>" +
            "   <if test=\"parentId!=null\"> " +
            "       parent_id," +
            "   </if>" +
            "   <if test=\"level!=null\"> " +
            "       level," +
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
            "   <if test=\"url!=null\"> " +
            "       #{url}," +
            "   </if>" +
            "   <if test=\"seq!=null\">" +
            "       #{seq}, " +
            "   </if>" +

            "   <if test=\"remark!=null\">" +
            "       #{remark}," +
            "   </if>" +
            "   <if test=\"parentId!=null\">" +
            "       #{parentId}, " +
            "   </if>" +
            "   <if test=\"level!=null\">" +
            "       #{level}, " +
            "   </if>" +
            "   <if test=\"operator!=null\">" +
            "       #{operator}, " +
            "   </if>" +
            "   <if test=\"operatorIp!=null\">" +
            "       #{operatorIp}, " +
            "   </if>" +
            " </trim>" +
            "</script>")
    int insertSeletive(SysMenu sysMenu);
}
