package com.zhaolq.mars.demo.annotation.annotation;

import java.lang.annotation.*;

/**
 * 表字段注解
 *
 * @author zhaolq
 * @since 2020/7/10 11:49
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SQLString {

    // 字段名
    String name() default "";

    // 字段默认值
    int value() default 0;

    // 约束条件
    Constraints constraint() default @Constraints;

}
