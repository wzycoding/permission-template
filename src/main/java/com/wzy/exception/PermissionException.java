package com.wzy.exception;

import lombok.Data;

/**
 * 权限管理异常
 */
@Data
public class PermissionException extends RuntimeException {

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;


    public PermissionException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }
}
