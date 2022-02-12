package com.zhaolq.mars.demo.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指明此类属于某个工厂类的注解
 *
 * @author zhaolq
 * @date 2020/7/10 10:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Factory {

    /**
     * 工厂类的名称
     */
    Class type();

    /**
     * 工厂类中使用，以确定应实例化哪个类的标识符
     */
    String id();
}