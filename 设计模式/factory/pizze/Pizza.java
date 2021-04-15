package com.handFirst.factory.pizze;

import com.handFirst.factory.ylFactory.yl.*;

import java.util.Arrays;

public abstract class Pizza {
    protected String name;
    protected Dough dough;
    protected Sauce sauce;
    protected Cheese cheese;
    protected Clams clams;
    protected Pepperoni pepperoni;
    protected Veggies[] veggies;

    public abstract void prepare();
    public  void bake(){
        System.out.println(name + " : " + "bake" );
    }
    public  void cut(){
        System.out.println(name + " : " + "cut" );
    }
    public  void box(){
        System.out.println(name + " : " + "box" );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", dough=" + dough +
                ", sauce=" + sauce +
                ", cheese=" + cheese +
                ", clams=" + clams +
                ", pepperoni=" + pepperoni +
                ", veggies=" + Arrays.toString(veggies) +
                '}';
    }
}
