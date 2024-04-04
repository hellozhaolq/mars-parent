package com.zhaolq.mars.common.core.function;

/**
 * 匹配接口
 *
 * @param <T> 匹配的对象类型
 *
 * @Author zhaolq
 * @Date 2023/6/8 23:24
 */
@FunctionalInterface
public interface Matcher<T> {
    /**
     * 给定对象是否匹配
     *
     * @param t 对象
     * @return 是否匹配
     */
    boolean match(T t);
}
