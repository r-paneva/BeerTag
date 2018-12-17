package com.beertag.services;

import com.beertag.models.Beer;
import com.beertag.models.MyBeers;
import com.beertag.models.MyBeersIdentity;
import com.beertag.repositories.base.MyBeersRepository;
import com.beertag.services.base.BeerService;
import com.beertag.services.base.MyBeersService;
import com.beertag.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBeersServiceImpl implements MyBeersService {
    private final MyBeersRepository mMyBeersRepository;
    private final UserService mUserService;
    private final BeerService mBeerService;

    @Autowired
    public MyBeersServiceImpl(
            MyBeersRepository myBeersRepository,
            UserService userService,
            BeerService beerService) {
        mMyBeersRepository = myBeersRepository;
        mUserService = userService;
        mBeerService = beerService;
    }

    @Override
    public List<MyBeers> getAll() {
        return mMyBeersRepository.getAll();
    }


    @Override
    public void CreateOrUpdateRatingVote(MyBeers myBeers) {
        int voterUserId = myBeers.getMyBeersIdentity().getUserId();
        int votedForBeerId = myBeers.getMyBeersIdentity().getBeerId();
        Beer votedForBeer = mBeerService.getBeerByID(votedForBeerId);
        MyBeers foundRatingVote = getRatingVoteByUsersVoterAndVotedFor(new MyBeersIdentity(voterUserId, votedForBeerId));
//        if (foundRatingVote != null) {
//            mRatingVoteRepository.delete(foundRatingVote);
//        }
//        MyBeers ratingVoteToReturn = new MyBeers();
//        myBeersIdentity.setUserId(voterUserId);
//        myBeersIdentity.setBeerId(votedForBeerId);
//        ratingVoteToReturn.setRatingVoted(ratingVoteDTO.getRatingVoted());
//        ratingVoteToReturn.setVotingDate(new Date());
//        mGenericService.create(ratingVoteToReturn);
//        votedForUser.setRating(getAverageRatingForUserByUsername(votedForUsername));
//        mGenericService.update(votedForUser.getId(), votedForUser);
    }


    @Override
    public MyBeers getRatingVoteByUsersVoterAndVotedFor(MyBeersIdentity myBeersIdentity) {
        return mMyBeersRepository.getRatingVoteByUsersVoterAndVotedFor(myBeersIdentity);
    }

    @Override
    public float getAverageRatingForBeerByBeerId(int votedForBeerId) {
        return mMyBeersRepository.getAverageRatingForBeerByBeerId(votedForBeerId);
    }

    @Override
    public List<MyBeers> getBeersByUserId(int id) {
        int a = 1;
        MyBeersIdentity myBeersIdentity = new MyBeersIdentity(1, id);
        return mMyBeersRepository.getBeersByUserId(myBeersIdentity);
    }
}
