package com.lxwde.spaghetti.jsr269.factory;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealTest {

    @Test
    public void getPrice() {
        Meal meal = MealFactory.create("Calzone");
        assertEquals( 8.5f, meal.getPrice(), 0.0);

    }
}