package com.anji.plus.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * BaseQueryBo--高级常用，分页查询
 * </pre>
 *
 * @author peiyanni
 * @version BaseQueryBO.java
 */
public class BaseQueryBO implements Serializable {
    private static final long serialVersionUID = -8871507943274741637L;

    private int pageNumber = 1;

    private int pageSize = 10;

    /**
     * 常用查询主键id
     */
    private Long commonId;

    /**
     * 排序字段名称组合，不传默认按id 倒序
     */
    private List<SortColumn> sortColumns;

    /**
     * 动态查询
     */
    private List<DynamicQueryBo> dynamicQueryBos;
    /**
     * 获取高级查询条件，排序
     */
    private QueryWrapper queryWrapper;


    public Long getCommonId() {
        return commonId;
    }

    public void setCommonId(Long commonId) {
        this.commonId = commonId;
    }

    public List<SortColumn> getSortColumns() {
        return sortColumns;
    }

    public void setSortColumns(List<SortColumn> sortColumns) {
        this.sortColumns = sortColumns;
    }

    public List<DynamicQueryBo> getDynamicQueryBos() {
        return dynamicQueryBos;
    }

    public void setDynamicQueryBos(List<DynamicQueryBo> dynamicQueryBos) {
        this.dynamicQueryBos = dynamicQueryBos;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public QueryWrapper getQueryWrapper() {
        return queryWrapper;
    }

    public void setQueryWrapper(QueryWrapper queryWrapper) {
        this.queryWrapper = queryWrapper;
    }
}
