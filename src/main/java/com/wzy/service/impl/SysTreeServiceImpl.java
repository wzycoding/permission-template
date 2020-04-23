package com.wzy.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.wzy.common.RequestHolder;
import com.wzy.dao.*;
import com.wzy.entity.SysAclModule;
import com.wzy.entity.SysDept;
import com.wzy.entity.SysMenu;
import com.wzy.service.ISysTreeService;
import com.wzy.util.LevelUtil;
import com.wzy.vo.AclModuleLevelVO;
import com.wzy.vo.DeptLevelVO;
import com.wzy.vo.MenuLevelVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 转树Service
 */
@Service
public class SysTreeServiceImpl implements ISysTreeService {
    @Override
    public List<DeptLevelVO> deptTree() {
        //1、查询出所有部门信息
        List<SysDept> sysDeptList = sysDeptMapper.list();

        List<DeptLevelVO> dtoList = Lists.newArrayList();
        //2、转化对象信息
        for (SysDept sysDept : sysDeptList) {
            DeptLevelVO deptLevelVO = DeptLevelVO.convert(sysDept);
            dtoList.add(deptLevelVO);
        }
        //3、转成部门树
        return deptToTree(dtoList);
    }

    @Override
    public List<AclModuleLevelVO> aclModuleTree() {
        //1.查出所有的权限模块信息
        List<SysAclModule> list = sysAclModuleMapper.list();

        List<AclModuleLevelVO> aclModuleVOList = Lists.newArrayList();
        //2.转化对象

        for (SysAclModule sysAclModule : list) {
            aclModuleVOList.add(AclModuleLevelVO.convert(sysAclModule));
        }
        //3.转成权限模块树
        return aclModuleToTree(aclModuleVOList);
    }

    @Override
    public List<MenuLevelVO> userMenuTree() {
        Long userId = RequestHolder.getCurrentUser().getId();

        //1.先获取当前用户所有的角色
        List<Long> roleIds = sysRoleMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        //2.获取角色下的所有菜单权限
        List<Long> menuIds = sysRoleMenuMapper.selectByRoleIdList(roleIds);
        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        //对菜单id进行去重
        Set<Long> targetMenuIds = Sets.newHashSet();
        targetMenuIds.addAll(menuIds);
        //3.对相同菜单id进行去重
        List<SysMenu> sysMenus = sysMenuMapper.selectByMenuIdList(new ArrayList<>(targetMenuIds));
        //4.转化为VO对象
        List<MenuLevelVO> menuLevelVOList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenus) {
            menuLevelVOList.add(MenuLevelVO.convert(sysMenu));
        }
        //5.转化成菜单树
        return userMenuToTree(menuLevelVOList);

    }

    private List<MenuLevelVO> userMenuToTree(List<MenuLevelVO> menuLevelVOList) {
        //如果数据源为空直接返回空集合
        if (CollectionUtils.isEmpty(menuLevelVOList)) {
            return Collections.emptyList();
        }

        Multimap<String, MenuLevelVO> menuLevelVOMap = ArrayListMultimap.create();

        List<MenuLevelVO> rootList = Lists.newArrayList();

        for (MenuLevelVO vo : menuLevelVOList) {
            menuLevelVOMap.put(vo.getLevel(), vo);
            if (vo.getLevel().equals(LevelUtil.ROOT)) {
                rootList.add(vo);
            }
        }

        //todo:将根节点List进行排序
        transformMenuLevelTree(rootList, LevelUtil.ROOT, menuLevelVOMap);
        return rootList;
    }

    private void transformMenuLevelTree(List<MenuLevelVO> rootList, String level, Multimap<String, MenuLevelVO> menuLevelVOMap) {
        for (MenuLevelVO vo : rootList) {
            //计算下一个层级
            String nextLevel = LevelUtil.calculateLevel(level, vo.getId());
            List<MenuLevelVO> childVOList = (List<MenuLevelVO>) menuLevelVOMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(childVOList)) {
                //todo:根据seq排序
                vo.setChildren(childVOList);
                transformMenuLevelTree(childVOList, nextLevel, menuLevelVOMap);
            }
        }
    }

    private List<AclModuleLevelVO> aclModuleToTree(List<AclModuleLevelVO> aclModuleVOList) {
        //如果数据源为空直接返回空集合
        if (CollectionUtils.isEmpty(aclModuleVOList)) {
            return Collections.emptyList();
        }

        Multimap<String, AclModuleLevelVO> aclModuleLevelMap = ArrayListMultimap.create();

        List<AclModuleLevelVO> rootList = Lists.newArrayList();

        for (AclModuleLevelVO aclModuleLevelVO : aclModuleVOList) {
            aclModuleLevelMap.put(aclModuleLevelVO.getLevel(), aclModuleLevelVO);
            if (aclModuleLevelVO.getLevel().equals(LevelUtil.ROOT)) {
                rootList.add(aclModuleLevelVO);
            }
        }

        //将根节点List进行排序
        rootList.sort(aclModuleSeqComparator);
        transformAclModuleTree(rootList, LevelUtil.ROOT, aclModuleLevelMap);
        return rootList;
    }

    /**
     * 转成权限模块树
     * @param rootList 根节点list
     * @param level 层级
     * @param aclModuleLevelMap 按照level分层的map
     */
    private void transformAclModuleTree(List<AclModuleLevelVO> rootList, String level, Multimap<String, AclModuleLevelVO> aclModuleLevelMap) {
        for (AclModuleLevelVO vo : rootList) {
            //计算下一个层级
            String nextLevel = LevelUtil.calculateLevel(level, vo.getId());
            List<AclModuleLevelVO> childVOList = (List<AclModuleLevelVO>) aclModuleLevelMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(childVOList)) {
                childVOList.sort(aclModuleSeqComparator);
                vo.setChildren(childVOList);
                transformAclModuleTree(childVOList, nextLevel, aclModuleLevelMap);
            }
        }
    }

    /**
     * 部门数据转树
     * @param deptLevelList 部门数据list
     * @return 部门树
     */
    private List<DeptLevelVO> deptToTree(List<DeptLevelVO> deptLevelList) {
        //如果源数据为空那么直接返回空集合
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }

        Multimap<String, DeptLevelVO> deptLevelMap = ArrayListMultimap.create();

        //一级部门，树形结构的根
        List<DeptLevelVO> rootList = Lists.newLinkedList();

        for (DeptLevelVO vo : deptLevelList) {
            //相同的level就会保存到相同的key下
            deptLevelMap.put(vo.getLevel(), vo);
            //如果为根节点，直接保存到根部的list中
            if (LevelUtil.ROOT.equals(vo.getLevel())) {
                rootList.add(vo);
            }
        }

        //将根节点List，按照seq排序
        rootList.sort(deptSeqComparator);

        //递归转树
        transformDeptTree(rootList, LevelUtil.ROOT, deptLevelMap);
        return rootList;
    }

    /**
     * 递归转部门树
     * @param deptLevelList 部门信息List
     * @param level 根节点
     * @param levelDeptMap 保存部门信息的map
     */
    private void transformDeptTree(List<DeptLevelVO> deptLevelList, String level, Multimap<String, DeptLevelVO> levelDeptMap) {
        for (DeptLevelVO deptLevelVO : deptLevelList) {
            //遍历该层的每一个元素
            //处理当前层级，最开始是root：0，计算下一个层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelVO.getId());
            //找到当前层级下的所有元素
            List<DeptLevelVO> childDeptList = (List<DeptLevelVO>) levelDeptMap.get(nextLevel);
            //如果子部门不等于空
            if (!CollectionUtils.isEmpty(childDeptList)) {
                //排序
                childDeptList.sort(deptSeqComparator);
                //设置子部门
                deptLevelVO.setChildren(childDeptList);
                //递归生成树
                transformDeptTree(childDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    /**
     * 部门的优先级比较器
     */
    private Comparator<DeptLevelVO> deptSeqComparator= new Comparator<DeptLevelVO>() {
        @Override
        public int compare(DeptLevelVO o1, DeptLevelVO o2) {
            if (o1.getSeq() < o2.getSeq()) {
                return 1;
            }
            if (o1.getSeq() > o2.getSeq()) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    /**
     * 权限模块优先级比较器
     */
    private Comparator<AclModuleLevelVO> aclModuleSeqComparator = new Comparator<AclModuleLevelVO>() {
        @Override
        public int compare(AclModuleLevelVO o1, AclModuleLevelVO o2) {
            if (o1.getSeq() < o2.getSeq()) {
                return 1;
            }
            if (o1.getSeq() > o2.getSeq()) {
                return -1;
            } else {
                return 0;
            }
        }
    };


    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;
}
