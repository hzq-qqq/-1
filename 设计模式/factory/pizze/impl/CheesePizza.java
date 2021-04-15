package com.handFirst.factory.pizze.impl;

import com.handFirst.factory.pizze.Pizza;
import com.handFirst.factory.ylFactory.PizzaIngredientFactory;

/**
 * @author hzq
 */
public class CheesePizza extends Pizza {
      PizzaIngredientFactory factory;

    public CheesePizza(PizzaIngredientFactory factory){
        name = "CheesePizza";
        this.factory = factory;
    }
    @Override
    public void prepare() {
        System.out.println("prepare: " + name);
       cheese = factory.createCheese();
       clams = factory.createClams();
       dough = factory.createDough();
       pepperoni = factory.createPepperoni();
       sauce = factory.createSauce();
       veggies = factory.createVeggies();
        System.out.println("CheesePizza prepare finish");
    }

    @Override
    public void bake() {
        System.out.println("CheesePizza bake");
    }

    @Override
    public void cut() {
        System.out.println("CheesePizza cut");
    }

    @Override
    public void box() {
        System.out.println("CheesePizza box");
    }

}
