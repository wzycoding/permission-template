package com.wzy.common;

import com.wzy.exception.PermissionException;

public enum ErrorEnum {
    /****************通用错误码*********************/
    SERVER_ERROR(10001, "服务器错误"),
    PARAM_ERROR(10002, "参数错误"),


    /****************权限错误码*********************/
    DEPT_NAME_EXIST(20001, "部门名称已存在"),
    USERNAME_OR_PASSWORD_ERROR(20002, "用户名或密码错误"),
    NOT_LOGIN(20003, "您未登录"),
    TOKEN_EXPIRED(20004, "登录信息已过期，请重新登录"),
    REPEAT_USERNAME(20005, "用户名已存在");

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void throwException() {
        throw new PermissionException(this.getCode(), this.msg);
    }

}
