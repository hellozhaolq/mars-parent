package com.zhaolq.core.utils;

/**
 * 字符串工具类
 *
 * 参考：
 *  org.apache.commons.lang3.StringUtils
 *  cn.hutool.core.util.StrUtil
 * @author zhaolq
 * @date 2020/11/12 11:44
 */
public class StringUtils {

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

}
