/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.tool.core.db.sql;

import com.zhaolq.mars.tool.core.utils.StringUtils;

/**
 * 排序方式（升序或者降序）
 *
 * @author zhaolq
 * @date 2022/2/28 19:32
 * @since 1.0.0
 */
public enum Direction {
    /** 升序 */
    ASC,
    /** 降序 */
    DESC;

    /**
     * 根据字符串值返回对应{@link Direction}值
     *
     * @param value 排序方式字符串，只能是 ASC或DESC
     * @return {@link Direction}
     * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value.
     */
    public static Direction fromString(String value) throws IllegalArgumentException {

        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(StringUtils.format("Invalid value [{}] for orders given! Has to be " + "either 'desc' or 'asc' (case insensitive).", value), e);
        }
    }
}
