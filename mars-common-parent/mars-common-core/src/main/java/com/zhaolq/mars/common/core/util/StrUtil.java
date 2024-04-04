package com.zhaolq.mars.common.core.util;

import org.apache.commons.lang3.StringUtils;

import com.zhaolq.mars.common.core.constant.StringPool;

/**
 * 字符串工具类
 *
 * @Author zhaolq
 * @Date 2023/6/9 20:47
 */
public class StrUtil {
    /**
     * 当给定字符串为null时，转换为Empty
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(CharSequence str) {
        return nullToDefault(str, StringPool.EMPTY);
    }

    /**
     * 如果字符串是 {@code null}，则返回指定默认字符串，否则返回字符串本身。
     *
     * <pre>
     * nullToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * nullToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
     * nullToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * nullToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str 要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(CharSequence str, String defaultStr) {
        return (str == null) ? defaultStr : str.toString();
    }

    /**
     * 截取分隔字符串之前的字符串，不包括分隔字符串<br>
     * 如果给定的字符串为空串（null或""）或者分隔字符串为null，返回原字符串<br>
     * 如果分隔字符串为空串""，则返回空串，如果分隔字符串未找到，返回原字符串，举例如下：
     *
     * <pre>
     * StrUtil.subBefore(null, *, false)      = null
     * StrUtil.subBefore("", *, false)        = ""
     * StrUtil.subBefore("abc", "a", false)   = ""
     * StrUtil.subBefore("abcba", "b", false) = "a"
     * StrUtil.subBefore("abc", "c", false)   = "ab"
     * StrUtil.subBefore("abc", "d", false)   = "abc"
     * StrUtil.subBefore("abc", "", false)    = ""
     * StrUtil.subBefore("abc", null, false)  = "abc"
     * </pre>
     *
     * @param string 被查找的字符串
     * @param separator 分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true为选取最后一个
     * @return 切割后的字符串
     */
    public static String subBefore(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (StringUtils.isEmpty(string) || separator == null) {
            return null == string ? null : string.toString();
        }

        final String str = string.toString();
        final String sep = separator.toString();
        if (sep.isEmpty()) {
            return StringPool.EMPTY;
        }
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == -1) {
            return str;
        }
        if (0 == pos) {
            return StringPool.EMPTY;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取分隔字符串之后的字符串，不包括分隔字符串<br>
     * 如果给定的字符串为空串（null或""），返回原字符串<br>
     * 如果分隔字符串为空串（null或""），则返回空串，如果分隔字符串未找到，返回空串，举例如下：
     *
     * <pre>
     * StrUtil.subAfter(null, *, false)      = null
     * StrUtil.subAfter("", *, false)        = ""
     * StrUtil.subAfter(*, null, false)      = ""
     * StrUtil.subAfter("abc", "a", false)   = "bc"
     * StrUtil.subAfter("abcba", "b", false) = "cba"
     * StrUtil.subAfter("abc", "c", false)   = ""
     * StrUtil.subAfter("abc", "d", false)   = ""
     * StrUtil.subAfter("abc", "", false)    = "abc"
     * </pre>
     *
     * @param string 被查找的字符串
     * @param separator 分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true为选取最后一个
     * @return 切割后的字符串
     */
    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (StringUtils.isEmpty(string)) {
            return null == string ? null : StringPool.EMPTY;
        }
        if (separator == null) {
            return StringPool.EMPTY;
        }
        final String str = string.toString();
        final String sep = separator.toString();
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == -1 || (string.length() - 1) == pos) {
            return StringPool.EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取指定字符串中间部分，不包括标识字符串<br>
     * <p>
     * 栗子：
     *
     * <pre>
     * StrUtil.subBetween("wx[b]yz", "[", "]") = "b"
     * StrUtil.subBetween(null, *, *)          = null
     * StrUtil.subBetween(*, null, *)          = null
     * StrUtil.subBetween(*, *, null)          = null
     * StrUtil.subBetween("", "", "")          = ""
     * StrUtil.subBetween("", "", "]")         = null
     * StrUtil.subBetween("", "[", "]")        = null
     * StrUtil.subBetween("yabcz", "", "")     = ""
     * StrUtil.subBetween("yabcz", "y", "z")   = "abc"
     * StrUtil.subBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str 被切割的字符串
     * @param before 截取开始的字符串标识
     * @param after 截取到的字符串标识
     * @return 截取后的字符串
     */
    public static String subBetween(CharSequence str, CharSequence before, CharSequence after) {
        if (str == null || before == null || after == null) {
            return null;
        }

        final String str2 = str.toString();
        final String before2 = before.toString();
        final String after2 = after.toString();

        final int start = str2.indexOf(before2);
        if (start != -1) {
            final int end = str2.indexOf(after2, start + before2.length());
            if (end != -1) {
                return str2.substring(start + before2.length(), end);
            }
        }
        return null;
    }
}
