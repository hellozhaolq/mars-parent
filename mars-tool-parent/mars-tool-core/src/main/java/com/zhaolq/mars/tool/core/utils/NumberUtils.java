package com.zhaolq.mars.tool.core.utils;

import cn.hutool.core.util.NumberUtil;

/**
 * 数字工具类
 * 对于精确值计算应该使用 BigDecimal
 * JDK7中BigDecimal(double val)构造方法的结果有一定的不可预知性，例如：
 *    new BigDecimal(0.1)
 * 表示的不是0.1而是0.1000000000000000055511151231257827021181583404541015625
 * 这是因为0.1无法准确的表示为double。因此应该使用new BigDecimal(String)。
 *
 * @author zhaolq
 * @date 2021/7/16 11:53
 */
public final class NumberUtils extends NumberUtil {
}
