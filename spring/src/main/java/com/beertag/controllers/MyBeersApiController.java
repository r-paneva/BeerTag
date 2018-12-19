package com.beertag.controllers;

import com.beertag.models.MyBeers;
import com.beertag.services.base.MyBeersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mybeers")
public class MyBeersApiController {
    private final MyBeersService mMyBeersService;

    @Autowired
    public MyBeersApiController(MyBeersService myBeersService) {
        mMyBeersService = myBeersService;
    }

    @RequestMapping (
            method = RequestMethod.GET
    )
    public List<MyBeers> getAll() {
        return mMyBeersService.getAll();
    }

    @RequestMapping(
            value = "beers/{id}",
            method = RequestMethod.GET
    )
    public List<MyBeers> getBeersByUserId(@PathVariable("id") int id) {
        return mMyBeersService.getBeersByUserId(id);
    }

    @RequestMapping(
            value = "/add",
            method = RequestMethod.POST
    )
    public void createOrUpdateMyBeer(@RequestBody MyBeers myBeers) {
         mMyBeersService.CreateOrUpdateRatingVote(myBeers);
    }
}