package com.zhaolq.mars.common.core.lang;

/**
 * 过滤器接口
 *
 * @author zhaolq
 * @date 2023/6/8 21:34
 */
@FunctionalInterface
public interface Filter<T> {
    /**
     * 是否接受对象
     *
     * @param t 检查的对象
     * @return 是否接受对象
     */
    boolean accept(T t);
}
