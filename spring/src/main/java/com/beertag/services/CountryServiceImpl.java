package com.beertag.services;

import com.beertag.models.Country;
import com.beertag.repositories.base.CountryRepository;
import com.beertag.services.base.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository mCountryRepository;


    @Autowired
    public CountryServiceImpl(
            CountryRepository countryRepository) {
        mCountryRepository = countryRepository;
    }

    @Override
    public List<Country> getAll() {
        return this.mCountryRepository.getAllCountries();
    }

    @Override
    public Country getCountryByID(int id) {
        return mCountryRepository.getCountryByID(id);
    }

    @Override
    public Country getByName(String name) {
        return mCountryRepository.getByName(name);
    }

}
