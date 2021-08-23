package com.lxwde.spaghetti.jsr269.factory;

import com.lxwde.spaghetti.jsr269.processor.Factory;

@Factory(id = "Margherita", type = Meal.class)
public class MargheritaPizza implements Meal {
    @Override
    public float getPrice() {
        return 6f;
    }
}
