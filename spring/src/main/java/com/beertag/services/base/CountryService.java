package com.beertag.services.base;

import com.beertag.models.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAll();
    Country getCountryByID(int id);
    Country getByName(String name);
}
