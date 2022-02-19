package com.zhaolq.mars.demo.annotation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类注解
 *
 * @author zhaolq
 * @date 2020/7/10 11:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DBTable {

    // 表名
    String name() default "";
}
