package com.beertag.models;

import javax.persistence.*;

@Entity

@Table(name = "userbeers")
public class UserBeers {

    @EmbeddedId
    private UserBeersIdentity userBeersIdentity;

    @Column(name = "vote")
    private float vote;

    @ManyToOne()
    @JoinColumn(name = "drink", nullable=false)
    private Drink drink;

    @ManyToOne()
    @JoinColumn(name = "beer", nullable=false)
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
