package com.zhaolq.demo.annotation.sample.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 披萨商店
 *
 * @author zhaolq
 * @date 2020/7/10 11:24
 */
public class PizzaStore {

    private MealFactory factory = new MealFactory();
    private MealFactoryOld factoryOld = new MealFactoryOld();

    public Meal order(String mealName) {
        return factory.create(mealName);
    }

    private static String readConsole() throws IOException {
        System.out.println("What do you like?");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferRead.readLine();
        return input;
    }

    public static void main(String[] args) throws IOException {
        PizzaStore pizzaStore = new PizzaStore();
        Meal meal = pizzaStore.order(readConsole());
        System.out.println("Bill: $" + meal.getPrice());
    }
}