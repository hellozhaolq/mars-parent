package com.zhaolq.demo.annotation.sample.processor;

import com.zhaolq.demo.annotation.Factory;

/**
 * 玛格丽特
 *
 * @author zhaolq
 * @date 2020/7/10 11:23
 */
@Factory(id = "Margherita", type = Meal.class)
public class MargheritaPizza implements Meal {

    @Override
    public float getPrice() {
        return 6f;
    }

}