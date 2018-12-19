package com.beertag.android.models;

import java.io.Serializable;


public class Drink implements Serializable {
    private int id;
    private String name;

    public Drink(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Drink() {
    }

    //Getters and setters omitted for brevity


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
