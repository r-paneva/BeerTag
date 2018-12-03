package com.beertag.repositories.base;

import com.beertag.models.Country;

import java.util.List;

public interface CountryRepository {

    Country getCountryByID(int id);

    Country getByName(String name);

    List<Country> getAllCountries();

    void update(int id, Country country);
}
