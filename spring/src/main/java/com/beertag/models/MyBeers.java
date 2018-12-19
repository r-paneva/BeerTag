package com.beertag.models;

import javax.persistence.*;

@Entity

@Table(name = "MyBeers")
public class MyBeers {

    @EmbeddedId
    private MyBeersIdentity myBeersIdentity;

    @Column(name = "vote")
    private float vote;

    @ManyToOne()
    @JoinColumn(name = "drink", nullable=false)
    private Drink drink;

    @ManyToOne()
    @JoinColumn(name = "beer", nullable=false)
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

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
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
