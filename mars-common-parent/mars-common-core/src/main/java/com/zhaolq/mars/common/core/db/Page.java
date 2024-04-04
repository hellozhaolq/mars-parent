/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.common.core.db;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import com.zhaolq.mars.common.core.db.sql.Order;

/**
 * 分页对象
 *
 * @Author zhaolq
 * @Date 2022/3/12 18:50
 * @Since 1.0.0
 */
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码，0表示第一页
     */
    private int pageNum;

    /**
     * 每页结果数
     */
    private int pageSize;

    /**
     * 排序
     */
    private Order[] orders;

    /**
     * 创建Page对象
     *
     * @return Page
     */
    public static Page of() {
        return new Page();
    }

    /**
     * 创建Page对象
     *
     * @param pageNum 页码，0表示第一页
     * @param pageSize 每页结果数
     * @return Page
     */
    public static Page of(int pageNum, int pageSize) {
        return new Page(pageNum, pageSize);
    }

    // ---------------------------------------------------------- Constructor start

    /**
     * 构造，默认第0页，每页{@value #DEFAULT_PAGE_SIZE} 条
     */
    public Page() {
        this(0, DEFAULT_PAGE_SIZE);
    }

    /**
     * 构造
     *
     * @param pageNum 页码，0表示第一页
     * @param pageSize 每页结果数
     */
    public Page(int pageNum, int pageSize) {
        this.pageNum = Math.max(pageNum, 0);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }


    /**
     * 构造
     *
     * @param pageNum 页码，0表示第一页
     * @param pageSize 每页结果数
     * @param order 排序对象
     */
    public Page(int pageNum, int pageSize, Order order) {
        this(pageNum, pageSize);
        this.orders = new Order[]{order};
    }

    // ---------------------------------------------------------- Constructor end

    // ---------------------------------------------------------- Getters and Setters start


    /**
     * @return 页码，0表示第一页
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 设置页码，0表示第一页
     *
     * @param pageNum 页码
     */
    public void setPageNum(int pageNum) {
        this.pageNum = Math.max(pageNum, 0);
    }

    /**
     * @return 每页结果数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页结果数
     *
     * @param pageSize 每页结果数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * @return 排序
     */
    public Order[] getOrders() {
        return this.orders;
    }

    /**
     * 设置排序
     *
     * @param orders 排序
     */
    public void setOrder(Order... orders) {
        this.orders = orders;
    }

    /**
     * 设置排序
     *
     * @param orders 排序
     */
    public void addOrder(Order... orders) {
        this.orders = ArrayUtils.addAll(this.orders, orders);
    }

    // ---------------------------------------------------------- Getters and Setters end

    /**
     * @return 开始位置
     */
    public int getStartPosition() {
        return PageUtil.getStart(this.pageNum, this.pageSize);
    }

    /**
     * @return 结束位置
     */
    public int getEndPosition() {
        return PageUtil.getEnd(this.pageNum, this.pageSize);
    }

    /**
     * 开始位置和结束位置<br>
     * 例如：
     *
     * <pre>
     * 页码：0，每页10 =》 [0, 10]
     * 页码：1，每页10 =》 [10, 20]
     * 页码：2，每页10 =》 [21, 30]
     * 。。。
     * </pre>
     *
     * @return 第一个数为开始位置，第二个数为结束位置
     */
    public int[] getStartEnd() {
        return PageUtil.transToStartEnd(pageNum, pageSize);
    }

    @Override
    public String toString() {
        return "Page [page=" + pageNum + ", pageSize=" + pageSize + ", order=" + Arrays.toString(orders) + "]";
    }
}
