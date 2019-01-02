package com.beertag.services.base;

import com.beertag.models.UserBeers;
import com.beertag.models.UserBeersIdentity;

import java.util.List;

public interface UserBeersService {
    void CreateOrUpdateRatingVote(UserBeers userBeers);
    List<UserBeers> getAll();
    List<UserBeers> getBeersByUserId(int id);
    UserBeers getRatingVoteByUsersVoterAndVotedFor(UserBeersIdentity userBeersIdentity);
    float getAverageRatingForBeerByBeerId(int votedForBeerId);
    void update(UserBeers beer);
    void create(UserBeers beer);
    void delete(UserBeers beer);
}
