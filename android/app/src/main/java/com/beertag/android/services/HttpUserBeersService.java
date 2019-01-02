package com.beertag.android.services;

import com.beertag.android.models.Drink;
import com.beertag.android.models.UserBeers;
import com.beertag.android.repositories.base.UserBeersRepository;
import com.beertag.android.services.base.UserBeersService;

import java.io.IOException;
import java.util.List;

public class HttpUserBeersService implements UserBeersService {

    private final UserBeersRepository mRatingVoteRepository;

    public HttpUserBeersService(
            UserBeersRepository ratingVoteRepository) {
        this.mRatingVoteRepository = ratingVoteRepository;
    }

    @Override
    public UserBeers createUserBeer(UserBeers userBeers) throws IOException {
        return mRatingVoteRepository.add(userBeers);
    }

    @Override
    public UserBeers updateRatingVote(int id, UserBeers userBeers) throws IOException {
        return mRatingVoteRepository.update(userBeers);
    }

    @Override
    public UserBeers deleteRatingVote(int id, UserBeers userBeers) throws IOException {
        return mRatingVoteRepository.delete(userBeers);
    }

    @Override
    public  List<UserBeers> getAll() throws IOException {
        return mRatingVoteRepository.getAll();
    }

    @Override
    public  List<UserBeers> getBeersByUserId (int userId) throws IOException {
        return mRatingVoteRepository.getBeersByUserId(userId);
    }

    @Override
    public UserBeers getRatingByBeerId (int beerId) throws IOException {
        return mRatingVoteRepository.getRatingByBeerId(beerId);
    }

    @Override
    public UserBeers getByDrink (Drink drink) throws IOException {
        return mRatingVoteRepository.getByDrink(drink);
    }

    @Override
    public UserBeers getById (int beerId, int userId) throws IOException {
        return mRatingVoteRepository.getById(beerId, userId);
    }
}
