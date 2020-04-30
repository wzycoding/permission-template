package com.wzy.service.impl;

import com.google.common.collect.Lists;
import com.wzy.dao.*;
import com.wzy.entity.SysRole;
import com.wzy.entity.SysUser;
import com.wzy.param.SysRoleParam;
import com.wzy.service.ISysRoleService;
import com.wzy.util.StringUtil;
import com.wzy.vo.SysRoleUserVO;
import com.wzy.vo.SysRoleVo;
import com.wzy.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色相关service
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Override
    public void insert(SysRoleParam param) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(param, sysRole);
        sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public List<SysRoleVo> list() {
        List<SysRole> list = sysRoleMapper.list();
        List<SysRoleVo> voList = Lists.newArrayList();
        for (SysRole sysRole : list) {
            SysRoleVo vo = new SysRoleVo();
            vo.setLabel(sysRole.getName());
            BeanUtils.copyProperties(sysRole, vo);
            voList.add(vo);
        }

        return voList;
    }

    @Override
    public SysRoleUserVO getUserListByRoleId(long roleId) {
        List<SysUser> allUserList = sysUserMapper.getAll();

        //被选中的id集合
        List<Long> selectUserIds = sysRoleUserMapper.getUserIdListByRoleId(roleId);

        //实体类List
        List<SysUser> selectedList = selectUserIds.stream().map(sysUserMapper::selectById).collect(Collectors.toList());

        List<SysUserVO> unselectedVoList = Lists.newArrayList();

        List<SysUserVO> selectedVoList = Lists.newArrayList();

        //过滤未被选中的用户
        for (SysUser sysUser : allUserList) {
            if (!selectUserIds.contains(sysUser.getId())) {
                SysUserVO vo = new SysUserVO();
                BeanUtils.copyProperties(sysUser, vo);
                unselectedVoList.add(vo);
            }
        }

        //转化选中的用户
        for (SysUser sysUser : selectedList) {
            SysUserVO vo = new SysUserVO();
            BeanUtils.copyProperties(sysUser, vo);
            selectedVoList.add(vo);
        }

        SysRoleUserVO roleUserVo = new SysRoleUserVO();
        roleUserVo.setSelectedUser(selectedVoList);
        roleUserVo.setUnSelectedUser(unselectedVoList);
        return roleUserVo;
    }

    @Override
    public void changeUsers(long roleId, String userIds) {
        List<Long> userIdList = StringUtil.splitToListLong(userIds);
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        //删除角色下所有的用户
        sysRoleUserMapper.removeByRoleId(roleId);
        sysRoleUserMapper.batchInsert(roleId, userIdList, "admin", "127.0.0.1");
    }

    @Override
    public void changeAcls(long roleId, String aclIds) {
        List<Long> aclIdList = StringUtil.splitToListLong(aclIds);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        //删除角色下所有权限点
        sysRoleAclMapper.removeByRoleId(roleId);
        sysRoleAclMapper.batchInsert(roleId, aclIdList, "admin", "127.0.0.1");
    }

    @Override
    public List<Long> getAclIdsByRoleId(long roleId) {
        return sysRoleAclMapper.getAclIdListByRoleId(roleId);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(long roleId) {
        return sysRoleMenuMapper.getMenuIdListByRoleId(roleId);
    }


    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
}
