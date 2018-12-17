package com.beertag.services.base;

import com.beertag.models.MyBeers;
import com.beertag.models.MyBeersIdentity;

import java.util.List;

public interface MyBeersService {
    void CreateOrUpdateRatingVote(MyBeers myBeers);
    List<MyBeers> getAll();
    List<MyBeers> getBeersByUserId(int id);
    MyBeers getRatingVoteByUsersVoterAndVotedFor(MyBeersIdentity myBeersIdentity);
    float getAverageRatingForBeerByBeerId(int votedForBeerId);
}
