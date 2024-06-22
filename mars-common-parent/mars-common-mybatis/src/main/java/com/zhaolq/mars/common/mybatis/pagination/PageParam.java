package com.zhaolq.mars.common.mybatis.pagination;

import com.github.pagehelper.IPage;

import io.mybatis.provider.Entity;

/**
 * 此类完全复制 {@link com.github.pagehelper.PageParam}，为了实体类继承后可以排除非数据库字段
 *
 * @Author zhaolq
 * @Date 2024/6/22 19:12
 */
public class PageParam implements IPage {
    @Entity.Transient
    private Integer pageNum;
    @Entity.Transient
    private Integer pageSize;
    @Entity.Transient
    private String orderBy;

    public PageParam() {
    }

    public PageParam(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageParam(Integer pageNum, Integer pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    @Override
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
