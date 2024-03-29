/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.common.core.enums;

/**
 * 换行符枚举<br>
 * 换行符包括：
 * <pre>
 * Mac系统换行符："\r"
 * Linux系统换行符："\n"
 * Windows系统换行符："\r\n"
 * </pre>
 *
 * @author zhaolq
 * @date 2022/3/12 17:24
 * @since 1.0.0
 */
public enum LineSeparator {
    /** Mac系统换行符："\r" */
    MAC("\r"),
    /** Linux系统换行符："\n" */
    LINUX("\n"),
    /** Windows系统换行符："\r\n" */
    WINDOWS("\r\n");

    private final String value;

    LineSeparator(String lineSeparator) {
        this.value = lineSeparator;
    }

    public String getValue() {
        return this.value;
    }
}
