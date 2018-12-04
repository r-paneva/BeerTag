package com.beertag.api;

import com.beertag.models.RatingVote;
import com.beertag.services.base.RatingVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
public class RatingVotesApiController {
    private final RatingVoteService mRatingVoteService;

    @Autowired
    public RatingVotesApiController(
            RatingVoteService ratingVoteService
    ) {
        mRatingVoteService = ratingVoteService;
    }
//
//    @RequestMapping(
//            value = "/add",
//            method = RequestMethod.POST
//    )
//    public void createOrUpdateRatingVote(@RequestBody RatingVote ratingVote) {
//         mRatingVoteService.CreateORUpdateRatingVote(ratingVote);
//    }
}
