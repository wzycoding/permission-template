package com.wzy.param;

import com.wzy.param.base.PageParam;
import lombok.Data;

/**
 * 权限点查询入参
 */
@Data
public class SysAclQueryParam extends PageParam {
    /**
     * 权限模块id
     */
    private Long aclModuleId;

    /**
     * 权限点名称
     */
    private String name;
}
