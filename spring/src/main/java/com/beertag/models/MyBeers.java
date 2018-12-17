package com.beertag.models;

import javax.persistence.*;

@Entity

@Table(name = "MyBeers")
public class MyBeers {

    @EmbeddedId
    private MyBeersIdentity myBeersIdentity;

    @Column(name = "vote")
    private Integer vote;

    @Column(name = "drink")
    private Integer drink;

    @ManyToOne()
    @JoinColumn(name = "beer", nullable=false)
    private Beer beer;

    public MyBeers(MyBeersIdentity myBeersIdentity, Integer vote, Integer drink, Beer beer) {
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

    public Integer getDrink() {
        return drink;
    }

    public void setDrink(Integer drink) {
        this.drink = drink;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }
}
