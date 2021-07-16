package com.zhaolq.mars.tool.core.utils;

import cn.hutool.core.util.PageUtil;

/**
 * 分页工具类
 * 并不是数据库分页的封装，而是分页方式的转换。
 * 在我们手动分页的时候，常常使用页码+每页个数的方式，但是有些数据库需要使用开始位置和结束位置来表示。
 * 很多时候这种转换容易出错（边界问题），于是封装了PageUtil工具类。
 *
 * @author zhaolq
 * @date 2021/7/16 11:19
 */
public final class PageUtils extends PageUtil {
}
