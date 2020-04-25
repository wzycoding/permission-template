package com.wzy.service.impl;

import com.wzy.common.ErrorEnum;
import com.wzy.dao.SysAclMapper;
import com.wzy.entity.SysAcl;
import com.wzy.param.SysAclParam;
import com.wzy.param.SysAclQueryParam;
import com.wzy.service.ISysAclService;
import com.wzy.vo.SysAclVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限点Service实现类
 */
@Service
public class SysAclServiceImpl implements ISysAclService {

    @Override
    public void save(SysAclParam param) {
        if (checkAclExist(param.getName(), param.getId())) {
            ErrorEnum.ACL_NAME_EXIST.throwException();
        }
        SysAcl sysAcl = SysAcl.builder().build();
        BeanUtils.copyProperties(param, sysAcl);
        sysAclMapper.insertSelective(sysAcl);
    }

    private boolean checkAclExist(String aclName, Long aclId) {
        return sysAclMapper.countByAclName(aclName, aclId) > 0;
    }

    @Override
    public void update(SysAclParam param) {
        if (checkAclExist(param.getName(), param.getId())) {
            ErrorEnum.ACL_NAME_EXIST.throwException();
        }
        SysAcl sysAcl = SysAcl.builder().build();
        BeanUtils.copyProperties(param, sysAcl);
        sysAclMapper.update(sysAcl);
    }

    @Override
    public List<SysAclVo> list(SysAclQueryParam param) {
        return sysAclMapper.list(param.getName(), param.getAclModuleId(), param.skip(), param.getSize());
    }

    @Override
    public SysAclVo getById(long aclId) {
        SysAcl sysAcl = sysAclMapper.selectById(aclId);
        SysAclVo vo = new SysAclVo();
        BeanUtils.copyProperties(sysAcl, vo);
        return vo;
    }

    @Override
    public void deleteById(long aclId) {
        sysAclMapper.deleteById(aclId);
    }

    @Override
    public int countList(SysAclQueryParam param) {
        return sysAclMapper.countList(param.getName(), param.getAclModuleId());
    }


    @Resource
    private SysAclMapper sysAclMapper;
}
