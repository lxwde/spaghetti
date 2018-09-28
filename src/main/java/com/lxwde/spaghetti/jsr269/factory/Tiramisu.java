package com.lxwde.spaghetti.jsr269.factory;

@Factory(id = "Tiramisu", type = Meal.class)
public class Tiramisu implements Meal {
    @Override
    public float getPrice() {
        return 4.5f;
    }
}
