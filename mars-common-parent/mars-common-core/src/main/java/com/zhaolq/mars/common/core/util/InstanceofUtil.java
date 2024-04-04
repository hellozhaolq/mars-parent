/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 2000-2099. All rights reserved.
 */

package com.zhaolq.mars.common.core.util;

/**
 * 向下转型工具类
 *
 * @Author zhaolq
 * @Date 2021/9/25 16:16
 */
public class InstanceofUtil {
    /**
     * 字符串转型判断
     *
     * @param val val
     * @return java.lang.String
     */
    public static <T> String instanceofString(T val) {
        String data = null;
        if (val != null) {
            data = String.valueOf(val);
        }
        return data;
    }

    /**
     * Boolean转型
     *
     * @param val val
     * @return java.lang.Boolean
     */
    public static <T> Boolean instanceofBoolean(T val) {
        Boolean data = null;
        if (val != null) {
            if (val instanceof Boolean) {
                data = (Boolean) val;
            } else {
                data = Boolean.parseBoolean(instanceofString(val));
            }
        }
        return data;
    }

    /**
     * 字符串转型判断
     *
     * @param val val
     * @return java.lang.Integer
     */
    public static <T> Integer instanceofInteger(T val) {
        Integer data = null;
        if (val != null) {
            data = Integer.parseInt(instanceofString(val));
        }
        return data;
    }
}
