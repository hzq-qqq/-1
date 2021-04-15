package com.handFirst.factory.main;

import com.handFirst.factory.pizzaStory.impl.BypPizzaStory;
import com.handFirst.factory.pizzaStory.PizzaStory;
import com.handFirst.factory.pizze.Pizza;
import com.handFirst.factory.ylFactory.PizzaIngredientFactory;
import com.handFirst.factory.ylFactory.impl.NypPizzaIngredientFactory;

public class Main {
    public static void main(String[] args) throws Exception {

        PizzaIngredientFactory factory = new NypPizzaIngredientFactory();

        PizzaStory story = new BypPizzaStory(factory);

        Pizza pizza = story.orderPizza("ChessesPizza");

    }
}
