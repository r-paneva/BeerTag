package com.beertag.android.services;

import com.beertag.android.models.RatingVote;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.base.RatingVoteService;

import java.io.IOException;

public class HttpRatingVoteService implements RatingVoteService {

    private final Repository<RatingVote> mRatingVoteRepository;

    public HttpRatingVoteService(
            Repository<RatingVote> ratingVoteRepository) {
        this.mRatingVoteRepository = ratingVoteRepository;
    }

    @Override
    public RatingVote createRatingVote(RatingVote ratingVote) throws IOException {
        return mRatingVoteRepository.add(ratingVote);
    }

    @Override
    public RatingVote updateRatingVote(int id, RatingVote ratingVote) throws IOException {
        return mRatingVoteRepository.update(ratingVote);
    }

    @Override
    public RatingVote getById(RatingVote ratingVote) throws IOException {
        return null;
    }
}
