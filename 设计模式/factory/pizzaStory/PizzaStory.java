package com.handFirst.factory.pizzaStory;

import com.handFirst.factory.pizze.Pizza;

public abstract class PizzaStory {

    public final Pizza orderPizza(String type) throws Exception {
        return creatPizza(type);
    }

    protected abstract Pizza creatPizza(String type) throws Exception;


}
