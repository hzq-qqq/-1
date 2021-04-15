package com.handFirst.封装算法.caffer;

public abstract class CaffeineBeveragge {
    public  final  void proper(){
        boilWater();
        brew();
        pourIncup();
        if(hook()){
            addCondiments();
        }

        hook();
    }

    /**
     * 这是一个钩子，由之类据决定是否需要实现该方法
     */
       public   boolean  hook(){
           return true;
       }

    protected abstract void addCondiments();

    protected abstract void brew();

    void boilWater(){
        System.out.println("boil Water !");
    }
    void pourIncup(){
        System.out.println("pouring into water !");
    }

}
