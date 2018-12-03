package com.beertag.android.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    public int id;
    private String userName;
    private String firstName;
    private String lastName;
    private List<Beer> drank;
    private List<Beer> want_to_drink;

    public User() {
        // public constructor is needed for parsing from/to JSON to work
    }

    public User(int id, String userName, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.drank = new ArrayList<>();
        this.want_to_drink = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Beer> getDrank() {
        return drank;
    }

    public void setDrank(List<Beer> drank) {
        this.drank = drank;
    }

    public List<Beer> getWant_to_drink() {
        return want_to_drink;
    }

    public void setWant_to_drink(List<Beer> want_to_drink) {
        this.want_to_drink = want_to_drink;
    }

}


