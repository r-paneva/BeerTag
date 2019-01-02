package com.beertag.android.models;

import java.io.Serializable;

public class UserBeers implements Serializable {

    private UserBeersIdentity userBeersIdentity;

    private Integer vote;
    private Drink drink;
    private Beer beer;

    public UserBeers(UserBeersIdentity userBeersIdentity, Integer vote, Drink drink, Beer beer) {
        this.userBeersIdentity = userBeersIdentity;
        setVote(vote);
        setDrink(drink);
        setBeer(beer);
    }

    public UserBeers() {
    }

    public UserBeersIdentity getUserBeersIdentity() {
        return userBeersIdentity;
    }

    public void setUserBeersIdentity(UserBeersIdentity userBeersIdentity) {
        this.userBeersIdentity = userBeersIdentity;
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
