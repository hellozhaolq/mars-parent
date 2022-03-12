/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 大约40亿年前-9999. All rights reserved.
 */

package com.zhaolq.mars.tool.core.utils;

import com.zhaolq.mars.tool.core.constant.StringPool;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 *
 * @author zhaolq
 * @date 2020/11/12 11:44
 */
public final class StringUtils extends StrUtil {
    /**
     * 空判断，忽略大小写
     *
     * @param str str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null ? true : StringPool.EMPTY.equalsIgnoreCase(str.trim());
    }

    /**
     * 禁止直接使用外部数据记录日志
     * <p>
     * 回车符 "\r"
     * 换行符 "\n"
     * Windows 换行 {@code "\r\n"}
     *
     * @param message message
     * @return java.lang.String
     */
    public static String replaceCRLF(String message) {
        if (message == null) {
            return "";
        }
        return message
                .replace(StringPool.CRLF, StringPool.UNDERLINE)
                .replace(StringPool.CR, StringPool.UNDERLINE)
                .replace(StringPool.LF, StringPool.UNDERLINE);
    }
}
