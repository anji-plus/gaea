package com.github.anji.plus.gaea.curd.params;

/**
 * 分页参数
 * @author lirui
 * @since 2020-11-23
 */
public class PageParam {

    /**
     * 页数
     */
    private Integer pageNumber = 1;

    /**
     * 每页的记录数
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String order;

    /**
     * 升序还是降序
     */
    private String sort;

    /**
     * 偏移量
     */
    private Integer offset;

    public Integer getOffset() {
        return (pageNumber - 1) * pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
