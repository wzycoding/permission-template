package com.wzy.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.wzy.common.RequestHolder;
import com.wzy.dao.*;
import com.wzy.entity.*;
import com.wzy.service.ISysTreeService;
import com.wzy.service.SysCoreService;
import com.wzy.util.LevelUtil;
import com.wzy.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<MenuLevelVO> allMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.listAll();
        // 转化为VO对象
        List<MenuLevelVO> menuLevelVOList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenus) {
            menuLevelVOList.add(MenuLevelVO.convert(sysMenu));
        }
        // 转化成菜单树
        return userMenuToTree(menuLevelVOList);
    }

    @Override
    public List<AclGeneralVO> roleAclTree(long roleId) {
        //1、先获取系统所有权限点信息
        List<SysAcl> allAclList = sysAclMapper.listAll();

        //2、获取角色的所有权限点id
        List<Long> roleAclIdList = sysRoleAclMapper.getAclIdListByRoleId(roleId);
        //3、获取角色权限点信息
        List<SysAcl> roleAclList = roleAclIdList.stream().map(sysAclMapper::selectById).collect(Collectors.toList());

        //4、获取当前用户的所有权限点
        List<SysAcl> currentUserAclList = sysCoreService.getCurrentUserAclList();

        //5、获取当前用户已分配的权限id
        Set<Long> userAclIdSet = currentUserAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());

        //6、获取当前角色的权限id
        Set<Long> roleAclIdSet = roleAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());

        //7、创建AclVoList
        List<AclVO> aclVOList = Lists.newArrayList();

        //8、选项转化对象，判断是否被选中
        for (SysAcl sysAcl : allAclList) {
            AclVO vo = new AclVO();
            BeanUtils.copyProperties(sysAcl, vo);
            // 非常重要的步骤！！！
            // 只有当前用户有的权限才能去给其他用户分配
            if (userAclIdSet.contains(sysAcl.getId())) {
                //允许分配
                vo.setHasAcl(true);
            }
            //判断当前角色权限点是否选中
            if (roleAclIdSet.contains(sysAcl.getId())) {
                //选中权限点
                vo.setChecked(true);
            }
            aclVOList.add(vo);
        }

        List<AclModuleLevelVO> rootList = roleAclToTree(aclVOList);
        return transformGeneralTree(rootList);
    }

    /**
     * 转成标准树的形式
     * @param rootList 原始树的根List
     * @return 角色树的标准树形式
     */
    private List<AclGeneralVO> transformGeneralTree(List<AclModuleLevelVO> rootList) {
        List<AclGeneralVO> voList = Lists.newArrayList();
        for (AclModuleLevelVO vo : rootList) {
            AclGeneralVO generalVO = new AclGeneralVO();
            //是否是权限点
            generalVO.setAcl(false);
            generalVO.setLabel(vo.getLabel());
            generalVO.setSeq(vo.getSeq());
            generalVO.setId(vo.getId());
            voList.add(generalVO);
            if (vo.getAclList().size() > 0) {
                for (AclVO aclVO : vo.getAclList()) {
                    AclGeneralVO generalAclVO = new AclGeneralVO();
                    generalAclVO.setAcl(true);
                    generalAclVO.setLabel(aclVO.getName());
                    generalAclVO.setSeq(aclVO.getSeq());
                    generalAclVO.setId(aclVO.getId());
                    generalAclVO.setHasAcl(!aclVO.isHasAcl());
                    //插入到children
                    generalVO.getChildren().add(generalAclVO);
                    //是否被选中
                    generalAclVO.setChecked(aclVO.isChecked());

                }
            }
            if (vo.getChildren().size() > 0 ) {
                generalVO.getChildren().addAll(transformGeneralTree(vo.getChildren()));
            }
        }
        return voList;
    }

    /**
     * 角色管理下的权限点列表转树
     * @param aclVOList 权限点列表
     * @return 权限树形列表
     */
    private List<AclModuleLevelVO> roleAclToTree(List<AclVO> aclVOList) {
        if (CollectionUtils.isEmpty(aclVOList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelVO> aclModuleLevelVOList = aclModuleTree();

        Multimap<Long, AclVO> moduleIdAclMap = ArrayListMultimap.create();
        for (AclVO aclVO : aclVOList) {
            moduleIdAclMap.put(aclVO.getAclModuleId(), aclVO);
        }
        bindAclsWithOrder(aclModuleLevelVOList, moduleIdAclMap);
        return aclModuleLevelVOList;
    }

    /**
     * 为权限模块绑定权限点
     * @param aclModuleLevelVOList 权限模块树
     * @param moduleIdAclMap 权限点Map
     */
    private void bindAclsWithOrder(List<AclModuleLevelVO> aclModuleLevelVOList, Multimap<Long, AclVO> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelVOList)) {
            return;
        }

        for (AclModuleLevelVO vo : aclModuleLevelVOList) {
            List<AclVO> aclVoList = (List<AclVO>) moduleIdAclMap.get(vo.getId());
            if (!CollectionUtils.isEmpty(aclVoList)) {
                //todo: 按照优先级排序
                vo.setAclList(aclVoList);
            }
            bindAclsWithOrder(vo.getChildren(), moduleIdAclMap);
        }
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

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysCoreService sysCoreService;
}
