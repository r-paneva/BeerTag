package com.beertag.api;

import com.beertag.models.Country;
import com.beertag.services.base.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            value = "/{id}",
            method = RequestMethod.GET
    )
    public Country getCountryByID(@PathVariable("id") int id) {
        return mCountryService.getCountryByID(id);
    }


}
