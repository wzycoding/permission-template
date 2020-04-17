package com.wzy.service.impl;

import com.wzy.common.ErrorEnum;
import com.wzy.common.RequestHolder;
import com.wzy.dao.SysDeptMapper;
import com.wzy.dao.SysUserMapper;
import com.wzy.entity.SysDept;
import com.wzy.param.SysDeptParam;
import com.wzy.service.ISysDeptService;
import com.wzy.util.IpUtil;
import com.wzy.util.LevelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统部门service实现类
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    /**
     * 保存部门信息
     * @param sysDeptParam 保存部门参数
     */
    @Override
    public void save(SysDeptParam sysDeptParam) {
        //判断当前层级下是否有同名的部门名称
        if (checkExist(sysDeptParam)) {
            ErrorEnum.DEPT_NAME_EXIST.throwException();
        }
        //构建部门对象
        SysDept sysDept = SysDept.builder().name(sysDeptParam.getName()).parentId(sysDeptParam.getParentId())
                .seq(sysDeptParam.getSeq()).remark(sysDeptParam.getRemark()).build();

        //设置层级
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(sysDeptParam.getParentId()), sysDeptParam.getParentId()));
        sysDept.setOperator("admin");
        sysDept.setOperatorIp("127.0.0.1");
        //插入数据
        sysDeptMapper.insertSelective(sysDept);
    }

    /**
     * 获取部门层级信息
     * @param deptId 部门id
     * @return 层级信息
     */
    private String getLevel(Long deptId) {
        SysDept sysDept = sysDeptMapper.get(deptId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }

    /**
     * 更新部门信息
     * @param param 修改部门入参
     */
    public void update(SysDeptParam param) {
        //判断当前层级下是否有同名的部门名称
        if (checkExist(param)) {
            ErrorEnum.DEPT_NAME_EXIST.throwException();
        }

        //判断当前更新部门是否存在
        SysDept before = sysDeptMapper.get(param.getId());

        if (before == null) {
            ErrorEnum.DEPT_NOT_EXIST.throwException();
        }

        SysDept after = SysDept.builder().build();
        BeanUtils.copyProperties(param, after);

        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator("admin");
        after.setOperatorIp("127.0.0.1");

        //更新子节点的层级关系
        updateWithChild(before, after);
        //todo:记录更新日志
        sysDeptMapper.update(after);

    }

    /**
     * 更新子节点层级关系
     * @param before 更新前
     * @param after 更新后
     */
    private void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(oldLevelPrefix);
            for (SysDept sysDept : deptList) {
                String level = sysDept.getLevel();
                //如果是oldLevelPrefix开头的部门
                if (level.indexOf(oldLevelPrefix) == 0) {
                    //0.1.2.3 被0.1 截取就是.2.3,拼接之后就变成：newLevelPrefix + ".2.3"
                    level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                    sysDept.setLevel(level);
                }
            }
            sysDeptMapper.batchUpdateLevel(deptList);
        }
    }

    /**
     * 检查部门名称是否冲突
     */
    private boolean checkExist(SysDeptParam param) {
        return sysDeptMapper.countByNameAndParentId(param.getParentId(), param.getName(), param.getId()) > 0;
    }

    /**
     * 删除部门信息
     * @param deptId 部门id
     */
    public void remove(long deptId) {
        SysDept sysDept = sysDeptMapper.get(deptId);
        if (sysDept == null) {
            ErrorEnum.DEPT_NOT_EXIST.throwException();
        }
        //判断是否存在子部门
        if (sysDeptMapper.countByParentId(sysDept.getParentId()) > 0) {
            ErrorEnum.DEPT_EXIST_CHILD.throwException();
        }
        //判断部门下面是否存在用户
        if (sysUserMapper.countByDeptId(sysDept.getId()) > 0 ) {
            ErrorEnum.DEPT_EXIST_USER.throwException();
        }
        //删除部门
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;
}
