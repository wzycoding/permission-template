package com.wzy.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wzy.dao.SysDeptMapper;
import com.wzy.entity.SysDept;
import com.wzy.service.ISysTreeService;
import com.wzy.util.LevelUtil;
import com.wzy.vo.DeptLevelVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        Collections.sort(rootList, deptSeqComparator);

        //递归转树
        transformDeptTree(deptLevelList, LevelUtil.ROOT, deptLevelMap);
        return rootList;
    }

    /**
     * 递归转部门树
     * @param deptLevelList 部门信息List
     * @param level 根节点
     * @param levelDeptMap 保存部门信息的map
     */
    private void transformDeptTree(List<DeptLevelVO> deptLevelList, String level, Multimap<String, DeptLevelVO> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            //遍历该层的每一个元素
            DeptLevelVO deptLevelVO = deptLevelList.get(i);
            //处理当前层级，最开始是root：0，计算下一个层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelVO.getId());
            //找到当前层级下的所有元素
            List<DeptLevelVO> childDeptList = (List<DeptLevelVO>) levelDeptMap.get(nextLevel);
            //如果子部门不等于空
            if (!CollectionUtils.isEmpty(childDeptList)) {
                //排序
                Collections.sort(childDeptList, deptSeqComparator);
                //设置子部门
                deptLevelVO.setChildren(childDeptList);
                //递归生成树
            }
        }
    }

    /**
     * 部门的优先级比较器
     */
    public Comparator<DeptLevelVO> deptSeqComparator= new Comparator<DeptLevelVO>() {
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


    @Resource
    private SysDeptMapper sysDeptMapper;
}
