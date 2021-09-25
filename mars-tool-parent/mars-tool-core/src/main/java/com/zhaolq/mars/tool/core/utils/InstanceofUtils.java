/*
 * Copyright (c) Zhaolq Technologies Co., Ltd. 2000-2099. All rights reserved.
 */

package com.zhaolq.mars.tool.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 向下转型工具类
 *
 * @author zhaolq
 * @since 2021/9/25 16:16
 */
public class InstanceofUtils {
    /**
     * List转型判断
     *
     * @param val val
     * @return java.util.List<T1>
     */
    public static <T1, T2> List<T1> instanceofList(T2 val) {
        List<T1> list = null;
        if (val != null) {
            if (val instanceof List) {
                list = (List<T1>) val;
            } else {
                list = new ArrayList<>();
            }
        }
        return list;
    }

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
     * Map转型
     *
     * @param val val
     * @return java.util.Map
     */
    public static <T> Map instanceofMap(T val) {
        Map data = null;
        if (val != null) {
            if (val instanceof Map) {
                data = (Map) val;
            } else {
                data = new HashMap();
            }
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
