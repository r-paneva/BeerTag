package com.beertag.services;

import com.beertag.models.Beer;
import com.beertag.models.Drink;
import com.beertag.models.UserBeers;
import com.beertag.models.UserBeersIdentity;
import com.beertag.repositories.base.UserBeersRepository;
import com.beertag.services.base.BeerService;
import com.beertag.services.base.UserBeersService;
import com.beertag.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBeersServiceImpl implements UserBeersService {
    private final UserBeersRepository mUserBeersRepository;
    private final UserService mUserService;
    private final BeerService mBeerService;

    @Autowired
    public UserBeersServiceImpl(
            UserBeersRepository userBeersRepository,
            UserService userService,
            BeerService beerService) {
        mUserBeersRepository = userBeersRepository;
        mUserService = userService;
        mBeerService = beerService;
    }

    @Override
    public List<UserBeers> getAll() {
        return mUserBeersRepository.getAll();
    }


    @Override
    public void CreateOrUpdateRatingVote(UserBeers userBeers) {
        int UserId = userBeers.getUserBeersIdentity().getUserId();
        int BeerId = userBeers.getUserBeersIdentity().getBeerId();
        Beer beer = userBeers.getBeer();
        float vote = userBeers.getVote();
        Drink drink = userBeers.getDrink();

        UserBeersIdentity userBeersIdentity = new UserBeersIdentity(BeerId, UserId);
        Beer votedForBeer = mBeerService.getBeerByID(BeerId);
        UserBeers foundRatingVote = getRatingVoteByUsersVoterAndVotedFor(userBeersIdentity);
        UserBeers ratingVoteToReturn = new UserBeers();

        if (foundRatingVote != null) {
            if(foundRatingVote.getDrink().getId()!= 0 && drink.getId()==0){
                drink = foundRatingVote.getDrink();
            }

            if(vote==0){
                vote = foundRatingVote.getVote();
            }

            mUserBeersRepository.delete(foundRatingVote);
        }

        ratingVoteToReturn.setUserBeersIdentity(userBeersIdentity);
        ratingVoteToReturn.setBeer(beer);
        ratingVoteToReturn.setDrink(drink);
        ratingVoteToReturn.setVote(vote);
        create(ratingVoteToReturn);
        votedForBeer.setRating(getAverageRatingForBeerByBeerId(votedForBeer.getId()));
        mBeerService.update(votedForBeer);
    }

    @Override
    public UserBeers getRatingVoteByUsersVoterAndVotedFor(UserBeersIdentity userBeersIdentity) {
        return mUserBeersRepository.getRatingVoteByUsersVoterAndVotedFor(userBeersIdentity);
    }

    @Override
    public float getAverageRatingForBeerByBeerId(int votedForBeerId) {
        return mUserBeersRepository.getAverageRatingForBeerByBeerId(votedForBeerId);
    }

    @Override
    public List<UserBeers> getBeersByUserId(int id) {
        int a = 1;
        UserBeersIdentity userBeersIdentity = new UserBeersIdentity(1, id);
        return mUserBeersRepository.getBeersByUserId(userBeersIdentity);
    }

    @Override
    public void update(UserBeers beer) {
        mUserBeersRepository.update(beer);
    }

    @Override
    public void create(UserBeers beer) {
        mUserBeersRepository.create(beer);
    }

    @Override
    public void delete(UserBeers beer) {
        mUserBeersRepository.delete(beer);
    }
}
