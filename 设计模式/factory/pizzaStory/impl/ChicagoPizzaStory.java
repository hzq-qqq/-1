package com.handFirst.factory.pizzaStory.impl;

import com.handFirst.factory.pizzaStory.PizzaStory;
import com.handFirst.factory.pizze.Pizza;
import com.handFirst.factory.pizze.impl.chess.CheesePizza1;

public  class ChicagoPizzaStory extends PizzaStory {


    @Override
    protected Pizza creatPizza(String type) throws Exception {
         Pizza pizza  = null;

         if ("chessesPizza".equals(type)) {
             pizza = new CheesePizza1();
             pizza.box();
             pizza.bake();
             return pizza;
         }else {

             System.out.println("no this Pizza");
            throw new Exception();
         }

    }
}
