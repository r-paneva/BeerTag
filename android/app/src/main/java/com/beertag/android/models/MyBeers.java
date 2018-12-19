package com.beertag.android.models;

import java.io.Serializable;

public class MyBeers implements Serializable {

    private MyBeersIdentity myBeersIdentity;

    private Integer vote;
    private Drink drink;
    private Beer beer;

    public MyBeers(MyBeersIdentity myBeersIdentity, Integer vote, Drink drink, Beer beer) {
        this.myBeersIdentity = myBeersIdentity;
        setVote(vote);
        setDrink(drink);
        setBeer(beer);
    }

    public MyBeers() {
    }

    public MyBeersIdentity getMyBeersIdentity() {
        return myBeersIdentity;
    }

    public void setMyBeersIdentity(MyBeersIdentity myBeersIdentity) {
        this.myBeersIdentity = myBeersIdentity;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }
}
