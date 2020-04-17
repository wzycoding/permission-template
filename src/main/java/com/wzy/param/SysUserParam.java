package com.wzy.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统用户入参
 */
@Data
public class SysUserParam {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 电话号码
     */
    @NotBlank(message = "电话号码不能为空")
    @Length(min = 0, max = 11, message = "电话号码最长不能超过11位")
    private String telephone;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 邮箱地址
     */
    @NotBlank(message = "邮箱地址不能为空")
    @Email(message = "请输入正确的email格式")
    private String mail;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    private String password;

    /**
     * 部门id
     */
    @NotNull(message = "必须指定用户所属部门")
    private Long deptId;



    /**
     * 用户备注
     */

    private String remark;

    /**
     * 用户状态：1为正常状态，0为冻结状态
     */
    private Integer enable;

    /**
     * 删除状态：1为已删除，0为未删除
     */
    private Integer deleted;
}
