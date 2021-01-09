package com.zhaolq.demo.annotation;

import java.lang.annotation.*;

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
