package com.beertag.android.services;

import com.beertag.android.models.Country;
import com.beertag.android.repositories.HttpCountryRepository;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.base.CountryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HttpCountryService implements CountryService {

    private final Repository<Country> mRepository;

    public HttpCountryService(Repository<Country> repository) {
        this.mRepository = repository;
    }

    @Override
    public List<Country> getAllCountries() throws IOException {
        return mRepository.getAll();
    }

    @Override
    public Country getById(int id) throws IOException {
        return (Country) mRepository.getById(id);
    }

    @Override
    public List<Country> getByName(String user) throws IOException {
        return null; //mRepository.getByName(user);
    }

    @Override
    public List<Country> getFilteredBeers(String pattern, String name) throws IOException {
        String patternToLower = pattern.toLowerCase();
        List<Country> list = new ArrayList<>();
        for (com.beertag.android.models.Country Country : getByName(name)) {
            if (Country.getName().toLowerCase().contains(patternToLower)) {
                list.add(Country);
            }
        }
        return list;
    }




}

