package com.beertag.repositories.base;

import com.beertag.models.UserBeers;
import com.beertag.models.UserBeersIdentity;

import java.util.List;


public interface UserBeersRepository {
    UserBeers getRatingVoteByUsersVoterAndVotedFor(UserBeersIdentity userBeersId);
    float getAverageRatingForBeerByBeerId(int votedForBeerId);
    List getAll();
    List getBeersByUserId(UserBeersIdentity userBeersIdentity);
    UserBeers getUserBeersById(UserBeersIdentity id);
    void create(UserBeers beer);
    void update(UserBeers beer);
    void delete(UserBeers beer);

}
