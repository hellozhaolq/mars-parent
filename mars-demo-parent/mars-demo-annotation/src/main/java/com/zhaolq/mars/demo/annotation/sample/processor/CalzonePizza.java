package com.zhaolq.mars.demo.annotation.sample.processor;

import com.zhaolq.mars.demo.annotation.annotation.Factory;

/**
 * 披萨饼
 *
 * @author zhaolq
 * @date 2020/7/10 11:22
 */
@Factory(id = "Calzone", type = Meal.class)
public class CalzonePizza implements Meal {

    @Override
    public float getPrice() {
        return 8.5f;
    }
}
