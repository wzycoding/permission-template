package com.wzy.param.base;

import javax.validation.constraints.Positive;

/**
 * 分页基类
 */
public class PageParam {
    /**
     * Positive注解校验是否是正数
     */
    @Positive
    private int page = 1;
    @Positive
    private int size = 10;

    public int skip()
    {
        return (page - 1) * size;
    }
    public int take()
    {
        return size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
