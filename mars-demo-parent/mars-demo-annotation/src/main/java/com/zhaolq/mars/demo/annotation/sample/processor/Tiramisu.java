package com.zhaolq.mars.demo.annotation.sample.processor;

import com.zhaolq.mars.demo.annotation.annotation.Factory;

/**
 * 提拉米苏
 *
 * @author zhaolq
 * @date 2020/7/10 11:24
 */
@Factory(id = "Tiramisu", type = Meal.class)
public class Tiramisu implements Meal {

    @Override
    public float getPrice() {
        return 4.5f;
    }

}