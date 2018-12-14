package com.beertag.android.services.base;

import com.beertag.android.models.RatingVote;

import java.io.IOException;

public interface RatingVoteService {
    RatingVote createRatingVote(RatingVote ratingVote) throws IOException;

    RatingVote updateRatingVote(int id, RatingVote ratingVote) throws IOException;

    RatingVote getById(RatingVote ratingVote) throws IOException;
}
