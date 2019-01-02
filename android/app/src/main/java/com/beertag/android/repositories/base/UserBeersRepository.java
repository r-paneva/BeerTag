package com.beertag.android.repositories.base;

import com.beertag.android.models.Drink;
import com.beertag.android.models.UserBeers;

import java.io.IOException;
import java.util.List;

public interface UserBeersRepository {

    List<UserBeers> getAll() throws IOException;

    UserBeers add(UserBeers item) throws IOException;

    UserBeers delete(UserBeers item) throws IOException;

    UserBeers update(UserBeers item) throws IOException;

    UserBeers getRatingByBeerId(int beerId) throws IOException;

    List <UserBeers> getBeersByUserId(int userId) throws IOException;

    UserBeers getByDrink(Drink drink) throws IOException;

    UserBeers getById (int beerId, int userId) throws IOException;
}
