package com.wzy.service.impl;

import com.wzy.common.ErrorEnum;
import com.wzy.common.RequestHolder;
import com.wzy.dao.SysDeptMapper;
import com.wzy.entity.SysDept;
import com.wzy.param.SysDeptParam;
import com.wzy.service.ISysDeptService;
import com.wzy.util.IpUtil;
import com.wzy.util.LevelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        if (checkExist(sysDeptParam.getParentId(), sysDeptParam.getName(), sysDeptParam.getId())) {
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
     * 检查部门名称是否冲突
     */
    private boolean checkExist(Long parentId, String deptName, Long id) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, id) > 0;
    }

    @Resource
    private SysDeptMapper sysDeptMapper;
}
