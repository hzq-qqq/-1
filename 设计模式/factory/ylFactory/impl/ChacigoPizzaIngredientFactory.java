package com.handFirst.factory.ylFactory.impl;

import com.handFirst.factory.ylFactory.PizzaIngredientFactory;
import com.handFirst.factory.ylFactory.yl.*;

public class ChacigoPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Cheese createCheese() {
        return new Cheese();
    }

    @Override
    public Clams createClams() {
        return new Clams();
    }

    @Override
    public Dough createDough() {
        return new Dough();
    }

    @Override
    public Pepperoni createPepperoni() {
        return new Pepperoni();
    }

    @Override
    public Sauce createSauce() {
        return new Sauce();
    }

    @Override
    public Veggies[] createVeggies() {
        return new Veggies[]{new Sc()};
    }
}
