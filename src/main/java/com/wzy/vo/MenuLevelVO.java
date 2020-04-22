package com.wzy.vo;

import com.google.common.collect.Lists;
import com.wzy.entity.SysMenu;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class MenuLevelVO {
    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 统一层级下的优先级
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 层级
     */
    private String level;
    private List<MenuLevelVO> children = Lists.newArrayList();

    /**
     * 转化方法
     * @param sysMenu 菜单信息实体类
     * @return 菜单显示视图类
     */
    public static MenuLevelVO convert(SysMenu sysMenu) {
        MenuLevelVO vo = new MenuLevelVO();
        BeanUtils.copyProperties(sysMenu, vo);
        return vo;
    }
}
