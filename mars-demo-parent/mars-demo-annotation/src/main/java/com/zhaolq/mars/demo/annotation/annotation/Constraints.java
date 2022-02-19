package com.zhaolq.mars.demo.annotation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 约束条件
 *
 * @author zhaolq
 * @date 2020/7/10 11:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Constraints {

    boolean primaryKey() default false;

    boolean allowNull() default false;

    boolean unique() default false;

}
