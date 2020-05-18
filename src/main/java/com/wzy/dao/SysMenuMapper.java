package com.wzy.dao;

import com.wzy.entity.SysMenu;
import com.wzy.param.SysMenuParam;
import org.apache.ibatis.annotations.*;

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

    @Select(" select * from sys_menu where deleted = 0")
    List<SysMenu> listAll();

    @Select(" select * from sys_menu where menuId = #{menuId}")
    SysMenu getById(long menuId);

    @Update(" update sys_menu set deleted = 1 where id = #{menuId}")
    int deleteById(long menuId);


    @Update(" <script>" +
            "   update sys_menu set" +
            "   <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "   <if test=\"id!=null\"> " +
            "       id = #{id}," +
            "   </if>" +
            "   <if test=\"name!=null\"> " +
            "       name = #{name}," +
            "   </if>" +
            "   <if test=\"url!=null\"> " +
            "       url = #{url}," +
            "   </if>" +
            "   <if test=\"seq!=null\"> " +
            "       seq = #{seq}," +
            "   </if>" +
            "   <if test=\"remark!=null\"> " +
            "       remark = #{remark}," +
            "   </if>" +
            "   <if test=\"parentId!=null\"> " +
            "       parent_id = #{parentId}," +
            "   </if>" +
            "   <if test=\"level!=null\"> " +
            "       level = #{level}," +
            "   </if>" +
            "   <if test=\"operator!=null\"> " +
            "       operator = #{operator}," +
            "   </if>" +
            "   <if test=\"operatorIp!=null\"> " +
            "       operator_ip = #{operatorIp}," +
            "   </if>" +
            "   </trim>" +
            "  where id = #{id}" +
            "</script>")
    int update(SysMenuParam param);
}
