package com.beertag.android.services.base;

import com.beertag.android.models.Country;

import java.io.IOException;
import java.util.List;

public interface CountryService {

    List<Country> getAllCountries() throws IOException;

    Country getById(int id) throws IOException;

    List<Country> getByName(String user) throws IOException;

    List<Country> getFilteredBeers(String pattern, String name) throws IOException;

}
