package com.wzy.service;

import com.wzy.vo.DeptLevelVO;

import java.util.List;

/**
 * 提供转树服务
 */
public interface ISysTreeService {

    List<DeptLevelVO> deptTree();
}
