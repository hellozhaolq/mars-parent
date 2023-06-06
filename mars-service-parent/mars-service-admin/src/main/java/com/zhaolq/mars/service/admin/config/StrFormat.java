package com.zhaolq.mars.service.admin.config;

import cn.hutool.core.text.StrFormatter;

/**
 * 字符串构造工具
 *
 * @author zhaolq
 * @date 2023/6/6 16:49:06
 * @since 1.0.0
 */
public class StrFormat {
    private StringBuilder stringBuilder = new StringBuilder();

    public static StrFormat init() {
        return new StrFormat();
    }

    /**
     * 头
     *
     * @return java.lang.StringBuilder
     */
    public StrFormat addHead() {
        stringBuilder
                .append(System.lineSeparator())
                .append("---------------------------------------------------------------------------------------")
                .append(System.lineSeparator());
        return this;
    }

    public StrFormat addTitle(String titleName) {
        stringBuilder.append("| ")
                .append(titleName)
                .append(": ")
                .append(System.lineSeparator());
        return this;
    }

    public StrFormat addContent(String key, String value) {
        stringBuilder.append("| ")
                .append(String.format("%30s", key))
                .append(": ")
                .append(value)
                .append(System.lineSeparator());
        return this;
    }

    public StrFormat addLine() {
        stringBuilder.append(System.lineSeparator());
        return this;
    }

    /**
     * 尾
     *
     * @return java.lang.StringBuilder
     */
    public StrFormat addTail() {
        stringBuilder.append("---------------------------------------------------------------------------------------");
        return this;
    }

    public String format() {
        return stringBuilder.toString();
    }
}
