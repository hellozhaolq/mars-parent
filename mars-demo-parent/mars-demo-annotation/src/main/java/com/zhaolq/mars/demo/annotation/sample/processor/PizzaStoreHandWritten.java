package com.zhaolq.mars.demo.annotation.sample.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author zhaolq
 * @Date 2020/7/10 11:24
 */
public class PizzaStoreHandWritten {

    private static String readConsole() throws IOException {
        System.out.println("What do you like?");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferRead.readLine();
        return input;
    }

    public static void main(String[] args) throws IOException {
        PizzaStoreHandWritten pizzaStore = new PizzaStoreHandWritten();
        Meal meal = pizzaStore.order(readConsole());
        System.out.println("Bill: $" + meal.getPrice());
    }

    public Meal order(String mealName) {

        if (mealName == null) {
            throw new IllegalArgumentException("Name of the meal is null!");
        }

        if ("Margherita".equals(mealName)) {
            return new MargheritaPizza();
        }

        if ("Calzone".equals(mealName)) {
            return new CalzonePizza();
        }

        if ("Tiramisu".equals(mealName)) {
            return new Tiramisu();
        }

        throw new IllegalArgumentException("Unknown meal '" + mealName + "'");
    }
}