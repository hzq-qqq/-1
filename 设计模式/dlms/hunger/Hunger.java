package com.handFirst.dlms.hunger;

import java.util.ArrayList;
import java.util.List;

public class Hunger {

    private Hunger(){

    };
   private final static List<Integer> ARRAY_LIST = new ArrayList<>();

   public static List<Integer> getInstance(){
    return ARRAY_LIST;
   }


}
