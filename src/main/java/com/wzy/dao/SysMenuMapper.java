package com.wzy.dao;

import com.wzy.entity.SysMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    int insertSelective(SysMenu sysMenu);


    @Select("<script>" +
            " select * from sys_menu where id in " +
            " <foreach collection='menuIdList' item='menuId' separator=',' open='(' close=\")\">" +
            "   #{menuId}" +
            " </foreach>" +
            "</script>")
    List<SysMenu> selectByMenuIdList(@Param("menuIdList") List<Long> menuIdList);
}
