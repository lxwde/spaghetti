package com.lxwde.spaghetti.jsr269.factory;

@Factory(id = "Margherita", type = Meal.class)
public class MargheritaPizza implements Meal {
    @Override
    public float getPrice() {
        return 6f;
    }
}
