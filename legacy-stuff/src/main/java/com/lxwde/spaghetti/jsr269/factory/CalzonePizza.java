package com.lxwde.spaghetti.jsr269.factory;

import com.lxwde.spaghetti.jsr269.processor.Factory;

@Factory(id = "Calzone", type = Meal.class)
public class CalzonePizza implements Meal{

    @Override
    public float getPrice() {
        return 8.5f;
    }
}
