/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-2022. All rights reserved.
 */

package com.zhaolq.mars.tool.core.sql;

import java.io.Serializable;

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

    /**
     * 排序的字段
     */
    private String field;
    /**
     * 排序方式（正序还是反序）
     */
    private Direction direction;

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
}
