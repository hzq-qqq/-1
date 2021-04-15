package com.handFirst.factory.pizzaStory.impl;

import com.handFirst.factory.pizzaStory.PizzaStory;
import com.handFirst.factory.pizze.Pizza;
import com.handFirst.factory.pizze.impl.CheesePizza;
import com.handFirst.factory.ylFactory.PizzaIngredientFactory;

public  class BypPizzaStory extends PizzaStory {
    PizzaIngredientFactory factory;

    public BypPizzaStory(PizzaIngredientFactory factory){
        this.factory = factory;
    }
    @Override
    protected Pizza creatPizza(String type) throws Exception {
        Pizza pizza  = null;

        if ("ChessesPizza".equals(type)) {
            pizza = new CheesePizza(factory);
            pizza.prepare();
            pizza.box();
            pizza.bake();
            pizza.cut();

            return pizza;
        }else {
            System.out.println("no this Pizza");
            throw new Exception();
        }

    }
}
