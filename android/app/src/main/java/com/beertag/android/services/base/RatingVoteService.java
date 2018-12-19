package com.beertag.android.services.base;

import com.beertag.android.models.Drink;
import com.beertag.android.models.MyBeers;

import java.io.IOException;
import java.util.List;

public interface RatingVoteService {

    List<MyBeers> getAll() throws IOException;

    MyBeers createMyBeer(MyBeers myBeers) throws IOException;

    MyBeers deleteRatingVote(int id, MyBeers myBeers) throws IOException;

    MyBeers updateRatingVote(int id, MyBeers myBeers) throws IOException;

    MyBeers getRatingByBeerId(int beerId) throws IOException;

    List <MyBeers> getBeersByUserId(int userId) throws IOException;

    MyBeers getByDrink(Drink drink) throws IOException;
}
