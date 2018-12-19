package com.beertag.services;

import com.beertag.models.Beer;
import com.beertag.models.Drink;
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
        int UserId = myBeers.getMyBeersIdentity().getUserId();
        int BeerId = myBeers.getMyBeersIdentity().getBeerId();
        Beer beer = myBeers.getBeer();
        float vote = myBeers.getVote();
        Drink drink = myBeers.getDrink();

        MyBeersIdentity myBeersIdentity = new MyBeersIdentity(BeerId, UserId);
        Beer votedForBeer = mBeerService.getBeerByID(BeerId);
        MyBeers foundRatingVote = getRatingVoteByUsersVoterAndVotedFor(myBeersIdentity);
        MyBeers ratingVoteToReturn = new MyBeers();

        if (foundRatingVote != null) {
            if(foundRatingVote.getDrink().getId()!= 0 && drink.getId()==0){
                drink = foundRatingVote.getDrink();
            }

            if(vote==0){
                vote = foundRatingVote.getVote();
            }

            mMyBeersRepository.delete(foundRatingVote);
        }

        ratingVoteToReturn.setMyBeersIdentity(myBeersIdentity);
        ratingVoteToReturn.setBeer(beer);
        ratingVoteToReturn.setDrink(drink);
        ratingVoteToReturn.setVote(vote);
        create(ratingVoteToReturn);
        votedForBeer.setRating(getAverageRatingForBeerByBeerId(votedForBeer.getId()));
        mBeerService.update(votedForBeer);
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

    @Override
    public void update(MyBeers beer) {
        mMyBeersRepository.update(beer);
    }

    @Override
    public void create(MyBeers beer) {
        mMyBeersRepository.create(beer);
    }

    @Override
    public void delete(MyBeers beer) {
        mMyBeersRepository.delete(beer);
    }
}
