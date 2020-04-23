package com.wzy.param;

import com.wzy.param.base.PageParam;
import lombok.Data;

@Data
public class SysUserQueryParam extends PageParam {
    private long deptId;
}
