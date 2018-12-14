package com.beertag.controllers;

import com.beertag.models.Beer;
import com.beertag.services.base.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beers")
public class BeersApiController {
    private final BeerService mBeerService;

    @Autowired
    public BeersApiController( BeerService beerService ) {
        mBeerService = beerService;
    }

    @RequestMapping (
            method = RequestMethod.GET
    )
    public List<Beer> getAll() {
        return mBeerService.getAll();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET
    )
    public Beer getBeerByID(@PathVariable("id") int id) {
        return mBeerService.getBeerByID(id);
    }

    @RequestMapping(
            method = RequestMethod.PUT
    )
    public void updateBeer(@RequestBody Beer beer) {
        mBeerService.update(beer);
    }

    @RequestMapping(
            method = RequestMethod.POST
    )
    public void createBeer(@RequestBody Beer beer) {
            mBeerService.create(beer);
    }

    @RequestMapping(
            method = RequestMethod.DELETE
    )
    public void deleteBeer(@RequestBody Beer beer) {
        mBeerService.delete(beer);
    }

}
