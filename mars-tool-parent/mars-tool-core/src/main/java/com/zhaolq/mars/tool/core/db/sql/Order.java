/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.tool.core.db.sql;

import java.io.Serializable;

import com.zhaolq.mars.tool.core.utils.StringUtils;

import lombok.Data;

/**
 * SQL排序对象
 *
 * @author zhaolq
 * @date 2022/2/28 19:29
 * @since 1.0.0
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 排序的字段 */
    private String field;
    /** 排序方式（正序还是反序） */
    private Direction direction;

    //---------------------------------------------------------- Constructor start
    public Order() {
    }

    /**
     * 构造
     *
     * @param field 排序字段
     */
    public Order(String field) {
        this.field = field;
    }

    /**
     * 构造
     *
     * @param field 排序字段
     * @param direction 排序方式
     */
    public Order(String field, Direction direction) {
        this(field);
        this.direction = direction;
    }

    //---------------------------------------------------------- Constructor end

    //---------------------------------------------------------- Getters and Setters start

    /**
     * @return 排序字段
     */
    public String getField() {
        return this.field;
    }

    /**
     * 设置排序字段
     *
     * @param field 排序字段
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return 排序方向
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * 设置排序方向
     *
     * @param direction 排序方向
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    //---------------------------------------------------------- Getters and Setters end

    @Override
    public String toString() {
        return StringUtils.builder().append(this.field).append(StringUtils.SPACE).append(null == direction ?
                StringUtils.EMPTY :
                direction).toString();
    }
}
