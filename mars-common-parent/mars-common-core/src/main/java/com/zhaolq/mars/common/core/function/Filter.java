package com.zhaolq.mars.common.core.function;

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

    /**
     * 执行函数，吃掉异常
     *
     * @return 是否接受对象
     */
    default boolean callNoThrowable(T t) {
        try {
            return accept(t);
        } catch (Throwable throwable) {
            return false;
        }
    }
}
