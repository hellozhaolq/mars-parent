package com.zhaolq.mars.demo.annotation.annotation;

import java.lang.annotation.*;

/**
 * 类注解
 *
 * @author zhaolq
 * @since 2020/7/10 11:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DBTable {

    // 表名
    String name() default "";
}
