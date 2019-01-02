package com.beertag.controllers;

import com.beertag.models.UserBeers;
import com.beertag.models.UserBeersIdentity;
import com.beertag.services.base.UserBeersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userbeers")
public class UserBeersApiController {
    private final UserBeersService mUserBeersService;

    @Autowired
    public UserBeersApiController(UserBeersService userBeersService) {
        mUserBeersService = userBeersService;
    }

    @RequestMapping (
            method = RequestMethod.GET
    )
    public List<UserBeers> getAll() {
        return mUserBeersService.getAll();
    }

    @RequestMapping(
            value = "user/{id}",
            method = RequestMethod.GET
    )
    public List<UserBeers> getBeersByUserId(@PathVariable("id") int id) {
        return mUserBeersService.getBeersByUserId(id);
    }

    @RequestMapping(
            value = "/add",
            method = RequestMethod.POST
    )
    public void createOrUpdateUserBeer(@RequestBody UserBeers userBeers) {
         mUserBeersService.CreateOrUpdateRatingVote(userBeers);
    }

    @RequestMapping(
            method = RequestMethod.PUT
    )
    public void updateBeer(@RequestBody UserBeers userBeers) {
        mUserBeersService.update(userBeers);
    }


    @RequestMapping(
            method = RequestMethod.DELETE
    )
    public void deleteBeer(@RequestBody UserBeers beer) {
        mUserBeersService.delete(beer);
    }

    @RequestMapping(
            value = "/{beerId}/{userId}",
            method = RequestMethod.GET
    )
    public UserBeers getUserBeerById(@PathVariable("beerId") int beerId, @PathVariable("userId") int userId) {
        UserBeersIdentity userBeersIdentity = new UserBeersIdentity(beerId, userId);
        return mUserBeersService.getRatingVoteByUsersVoterAndVotedFor(userBeersIdentity);
    }


}
