package com.beertag.android.services;

import com.beertag.android.models.Drink;
import com.beertag.android.models.MyBeers;
import com.beertag.android.repositories.base.RatingRepository;
import com.beertag.android.services.base.RatingVoteService;

import java.io.IOException;
import java.util.List;

public class HttpRatingVoteService implements RatingVoteService {

    private final RatingRepository mRatingVoteRepository;

    public HttpRatingVoteService(
            RatingRepository ratingVoteRepository) {
        this.mRatingVoteRepository = ratingVoteRepository;
    }

    @Override
    public MyBeers createMyBeer(MyBeers myBeers) throws IOException {
        return mRatingVoteRepository.add(myBeers);
    }

    @Override
    public MyBeers updateRatingVote(int id, MyBeers myBeers) throws IOException {
        return mRatingVoteRepository.update(myBeers);
    }

    @Override
    public MyBeers deleteRatingVote(int id, MyBeers myBeers) throws IOException {
        return mRatingVoteRepository.delete(myBeers);
    }

    @Override
    public  List<MyBeers> getAll() throws IOException {
        return mRatingVoteRepository.getAll();
    }

    @Override
    public  List<MyBeers> getBeersByUserId (int userId) throws IOException {
        return mRatingVoteRepository.getBeersByUserId(userId);
    }

    @Override
    public  MyBeers getRatingByBeerId (int beerId) throws IOException {
        return mRatingVoteRepository.getRatingByBeerId(beerId);
    }

    @Override
    public  MyBeers getByDrink (Drink drink) throws IOException {
        return mRatingVoteRepository.getByDrink(drink);
    }

    @Override
    public  MyBeers getById (int beerId, int userId) throws IOException {
        return mRatingVoteRepository.getById(beerId, userId);
    }
}
