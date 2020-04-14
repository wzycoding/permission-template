package com.wzy.common;

import lombok.Data;

/**
 * 返回结果包装类
 * @param <T> 泛型类型
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }



    public static Result error(int code, String msg) {
        return new Result(code, msg);
    }

    public static Result error(ErrorEnum errorEnum) {
        return new Result(errorEnum.getCode(), errorEnum.getMsg());
    }

    public static <T> Result success(T data) {
        return new Result(0, "", data);
    }

    public static <T> Result success() {
        return new Result(0, "");
    }
}
