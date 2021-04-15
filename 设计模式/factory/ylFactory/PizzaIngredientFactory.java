package com.handFirst.factory.ylFactory;

import com.handFirst.factory.ylFactory.yl.*;

public interface PizzaIngredientFactory {
    Cheese createCheese();
    Clams createClams();
    Dough createDough();
    Pepperoni createPepperoni();
    Sauce createSauce();
    Veggies[] createVeggies();
}
