/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.common.core.enums;

/**
 * 操作系统枚举
 *
 * @Author zhaolq
 * @Date 2022/8/22 20:52
 * @Since 1.0.0
 */
public enum OS {

    /** unix */
    UNIX("unix"),
    /** linux */
    LINUX("linux"),
    /** windows */
    WINDOWS("windows");

    // ---------------------------------------------------------------

    private final String value;

    private OS(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
