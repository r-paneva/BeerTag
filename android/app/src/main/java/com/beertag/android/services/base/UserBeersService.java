package com.beertag.android.services.base;

import com.beertag.android.models.Drink;
import com.beertag.android.models.UserBeers;

import java.io.IOException;
import java.util.List;

public interface UserBeersService {

    List<UserBeers> getAll() throws IOException;

    UserBeers createUserBeer(UserBeers userBeers) throws IOException;

    UserBeers deleteRatingVote(int id, UserBeers userBeers) throws IOException;

    UserBeers updateRatingVote(int id, UserBeers userBeers) throws IOException;

    UserBeers getRatingByBeerId(int beerId) throws IOException;

    List <UserBeers> getBeersByUserId(int userId) throws IOException;

    UserBeers getByDrink(Drink drink) throws IOException;

    UserBeers getById (int beerId, int userId) throws IOException;
}
