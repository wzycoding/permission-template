package com.wzy.vo;

import com.wzy.entity.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AclVO extends SysAclVO {
    /**
     * 是否被选中, 默认未被选中
     */
    private boolean checked = false;

    /**
     * 是否有权限操作：能分配的权限要小于自己的权限
     */
    private boolean hasAcl = false;

    /**
     * 对象转化
     * @param sysAcl 权限点对象
     * @return 权限点信息
     */
    public static AclVO convert(SysAcl sysAcl) {
        AclVO vo = new AclVO();
        BeanUtils.copyProperties(sysAcl, vo);
        return vo;
    }

}
