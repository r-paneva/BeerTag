package com.beertag.services;

import com.beertag.models.MyBeersIdentity;
import com.beertag.models.RatingVote;
import com.beertag.models.User;
import com.beertag.repositories.base.RatingVotesRepository;
import com.beertag.services.base.RatingVoteService;
import com.beertag.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RatingVoteServiceImpl implements RatingVoteService {
//    private final RatingVotesRepository mRatingVoteRepository;
//    private final UserService mUserService;
//
//    @Autowired
//    public RatingVoteServiceImpl(
//            RatingVotesRepository ratingVoteRepository,
//            UserService userService
//    ) {
//        mRatingVoteRepository = ratingVoteRepository;
//        mUserService = userService;
//    }
//
//    @Override
//    public void CreateORUpdateRatingVote(RatingVote ratingVote) {
//        String voterUserName=ratingVote.getVoterUsername();
//        String votedForUsername=ratingVote.getVotedForUserUsername();
//        User votedForUser=mUserService.getByUserName(votedForUsername);
//        RatingVote foundRatingVote=getRatingVoteByUsersVoterAndVotedFor(voterUserName,votedForUsername);
//        if(foundRatingVote!=null){
//            mRatingVoteRepository.delete(foundRatingVote);
//        }
//        RatingVote  ratingVoteToReturn = new RatingVote();
//        ratingVoteToReturn.setVoter(voterUserName);
//        ratingVoteToReturn.setVotedForUser(votedForUser);
//        ratingVoteToReturn.setRatingVoted(ratingVoteDTO.getRatingVoted());
//        ratingVoteToReturn.setVotingDate(new Date());
//        mGenericService.create(ratingVoteToReturn);
//        votedForUser.setRating(getAverageRatingForUserByUsername(votedForUsername));
//        mGenericService.update(votedForUser.getId(),votedForUser);
//    }
//
//
//    @Override
//    public RatingVote getRatingVoteByUsersVoterAndVotedFor(MyBeersIdentity myBeersIdentity){
//        return mRatingVoteRepository.getRatingVoteByUsersVoterAndVotedFor(myBeersIdentity);
//    }
//
//    @Override
//    public float getAverageRatingForUserByUsername(String votedForUsername) {
//        return mRatingVoteRepository.getAverageRatingForUserByUsername(votedForUsername);
//    }
}
