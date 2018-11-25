package com.beertag.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Entity

@Table(name = "RatingVote")
public class RatingVote {

    @EmbeddedId
    private MyBeersIdentity myBeersIdentity;

    @Column(name = "Vote")
    private Integer vote;

    public RatingVote(MyBeersIdentity myBeersIdentity, Integer vote) {
        this.myBeersIdentity = myBeersIdentity;
        this.vote = vote;
    }

    public RatingVote() {
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
}
