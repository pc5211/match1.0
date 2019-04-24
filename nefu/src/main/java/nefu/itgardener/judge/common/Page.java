/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package nefu.itgardener.judge.common;

/**
 * @author : Hunter
 * @date : 18-4-22
 * @since : Java 8
 */
public class Page {

    /**
     * 当前页码
     */
    private Integer nowPage;

    /**
     * 总页码数
     */
    private Integer totalPage;

    /**
     * 每页数据量,默认 5
     */
    private Integer pageSize = 5;

    /**
     * 数据总量
     */
    private Integer totalSize;

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
}
