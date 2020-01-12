package com.example.homedical;

import java.util.ArrayList;

public class User1 {
    String name;
   ArrayList<Medical> m;
   int stutos;

   public User1(){}
    public User1(String n , int s){
        this.name= n;
        m = new ArrayList<>();
        stutos=s;
    }
    public String getName (){
        return this.name;
    }
    public int getstutos (){
        return this.stutos;
    }
    public void Add(Medical k){
        m.add(k);
    }
}

