package com.beertag.controllers;

import com.beertag.models.Country;
import com.beertag.services.base.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryApiController {
    private final CountryService mCountryService;

    @Autowired
    public CountryApiController(CountryService countryService) {
        mCountryService = countryService;
    }

    @RequestMapping (
            method = RequestMethod.GET
    )
    public List<Country> getAll() {
        return mCountryService.getAll();
    }

    @RequestMapping(
            value = "/id/{id}",
            method = RequestMethod.GET
    )
    public Country getCountryByID(@PathVariable("id") int id) {
        return mCountryService.getCountryByID(id);
    }

    @RequestMapping(
            path = "/name/{name}",
            method = RequestMethod.GET
    )
    public Country getByName(@PathVariable("name") String name) {
        return mCountryService.getByName(name);
    }

    @RequestMapping(
            method = RequestMethod.PUT
    )
    public void updateCountry(@RequestBody Country country) {
        mCountryService.update(country);
    }



}
