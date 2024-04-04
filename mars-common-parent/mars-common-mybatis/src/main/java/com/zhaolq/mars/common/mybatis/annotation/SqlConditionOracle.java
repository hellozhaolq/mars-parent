package com.zhaolq.mars.common.mybatis.annotation;

import com.baomidou.mybatisplus.annotation.SqlCondition;

/**
 * Oracle SQL比较条件常量定义类
 *
 * @Author zhaolq
 * @Date 2021/5/31 22:28
 */
public class SqlConditionOracle extends SqlCondition {

    /**
     * % 两边 %
     */
    public static final String LIKE = "%s LIKE '%%' || #{%s} || '%%'";
    /**
     * % 左
     */
    public static final String LIKE_LEFT = "%s LIKE '%%' || #{%s}";
    /**
     * 右 %
     */
    public static final String LIKE_RIGHT = "%s LIKE #{%s} || '%%'";

}
