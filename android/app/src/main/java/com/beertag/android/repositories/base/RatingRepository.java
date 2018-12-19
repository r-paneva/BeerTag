package com.beertag.android.repositories.base;

import com.beertag.android.models.Drink;
import com.beertag.android.models.MyBeers;

import java.io.IOException;
import java.util.List;

public interface RatingRepository {

    List<MyBeers> getAll() throws IOException;

    MyBeers add(MyBeers item) throws IOException;

    MyBeers delete(MyBeers item) throws IOException;

    MyBeers update(MyBeers item) throws IOException;

    MyBeers getRatingByBeerId(int beerId) throws IOException;

    List <MyBeers> getBeersByUserId(int userId) throws IOException;

    MyBeers getByDrink(Drink drink) throws IOException;

    MyBeers getById (int beerId, int userId) throws IOException;
}
