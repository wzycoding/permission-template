package com.wzy.service.impl;

import com.wzy.common.ErrorEnum;
import com.wzy.dao.SysAclModuleMapper;
import com.wzy.entity.SysAclModule;
import com.wzy.param.SysAclModuleParam;
import com.wzy.service.ISysAclModuleService;
import com.wzy.util.LevelUtil;
import com.wzy.vo.SysAclModuleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysAclModuleServiceImpl implements ISysAclModuleService {
    @Override
    public void save(SysAclModuleParam param) {
        if (checkAclModuleNameExist(param)) {
            ErrorEnum.ACL_MODULE_NAME_EXIST.throwException();
        }
        SysAclModule sysAclModule = SysAclModule.builder().build();
        BeanUtils.copyProperties(param, sysAclModule);
        //设置层级
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
//        sysAclModule.setOperator("admin");
//        sysAclModule.setOperatorIp("127.0.0.1");
        //插入数据
        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    private String getLevel(Long parentId) {
        SysAclModule sysAclModule = sysAclModuleMapper.get(parentId);
        if (sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }

    /**
     * 检查同意权限模块下是否有同名的权限模块名称
     * @param param 入参
     * @return 是否重复
     */
    private boolean checkAclModuleNameExist(SysAclModuleParam param) {
        return sysAclModuleMapper.countByNameAndParentId(param.getParentId(), param.getName(), param.getId()) > 0;
    }

    @Override
    public void update(SysAclModuleParam param) {
        if (checkAclModuleNameExist(param)) {
            ErrorEnum.ACL_MODULE_NAME_EXIST.throwException();
        }

        //判断权限模块是否存在
        SysAclModule before = sysAclModuleMapper.get(param.getId());
        if (before == null) {
            ErrorEnum.ACL_MODULE_NOT_EXIST.throwException();
        }

        SysAclModule after = SysAclModule.builder().build();

        BeanUtils.copyProperties(param, after);
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
//        after.setOperator("admin");
//        after.setOperatorIp("127.0.0.1");
        sysAclModuleMapper.update(after);
        updateWithChild(before, after);
    }

    /**
     * 更新子元素的层级
     * @param before 更新前
     * @param after 更新后
     */
    private void updateWithChild(SysAclModule before, SysAclModule after) {
        String beforeLevel = before.getLevel();
        String afterLevel = after.getLevel();

        String beforeChildLevel = LevelUtil.calculateLevel(beforeLevel, before.getId());
        if (!beforeLevel.equals(afterLevel)) {
            List<SysAclModule> childAclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(beforeLevel);
            for (SysAclModule sysAclModule : childAclModuleList) {
                String level = sysAclModule.getLevel();
                if (sysAclModule.getLevel().indexOf(beforeChildLevel) == 0) {
                    level = afterLevel + level.substring(beforeLevel.length());
                    sysAclModule.setLevel(level);
                }
            }
            if (childAclModuleList.size() > 0) {
                sysAclModuleMapper.batchUpdateLevel(childAclModuleList);
            }
        }
    }

    @Override
    public void remove(long aclModuleId) {
        if (sysAclModuleMapper.get(aclModuleId) == null) {
            ErrorEnum.ACL_MODULE_NOT_EXIST.throwException();
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }

    @Override
    public SysAclModuleVO get(long aclModuleId) {
        SysAclModule sysAclModule = sysAclModuleMapper.get(aclModuleId);
        SysAclModuleVO sysAclModuleVO = new SysAclModuleVO();
        BeanUtils.copyProperties(sysAclModule, sysAclModuleVO);
        return sysAclModuleVO;
    }



    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
}
