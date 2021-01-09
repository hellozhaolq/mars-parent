package com.zhaolq.demo.annotation;

import java.lang.annotation.*;

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
