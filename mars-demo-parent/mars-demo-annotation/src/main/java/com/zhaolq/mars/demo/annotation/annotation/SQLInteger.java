package com.zhaolq.mars.demo.annotation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表字段注解
 *
 * @author zhaolq
 * @date 2020/7/10 11:49
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLInteger {

    // 字段名
    String name() default "";

    // 约束条件
    Constraints constaint() default @Constraints;

}
