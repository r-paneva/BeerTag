package com.beertag.repositories.base;

import com.beertag.models.Beer;
import com.beertag.models.MyBeers;
import com.beertag.models.MyBeersIdentity;

import java.util.List;


public interface MyBeersRepository{
    MyBeers getRatingVoteByUsersVoterAndVotedFor(MyBeersIdentity myBeersId);
    float getAverageRatingForBeerByBeerId(int votedForBeerId);
    List getAll();
    List getBeersByUserId(MyBeersIdentity myBeersIdentity);
    MyBeers getMyBeersById(MyBeersIdentity id);
    void create(MyBeers beer);
    void update(MyBeers beer);
    void delete(MyBeers beer);

}
