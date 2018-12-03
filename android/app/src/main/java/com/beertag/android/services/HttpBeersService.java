package com.beertag.android.services;


import com.beertag.android.models.Beer;
import com.beertag.android.repositories.HttpBeerRepository;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.base.BeersService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HttpBeersService implements BeersService {
    private final Repository<Beer> mRepository;

    public HttpBeersService(Repository<Beer> repository) {
        this.mRepository = repository;
    }

    @Override
    public List<Beer> getAllBeers() throws IOException {
        return mRepository.getAll();
    }

    @Override
    public List<Beer> getBeersByStyle(String userName) throws IOException {
        //return mRepository.getBeersByStyle(userName);
        return null;
    }

    @Override
    public List<Beer> getBeersByUser(String userName) throws IOException {
        //return mRepository.getBeersByUser(userName);
        return null;
    }

    @Override
    public Beer getDetailsById(int id) throws IOException {
        return (Beer) mRepository.getById(id);
    }

    @Override
    public List<Beer> getFilteredBeers(String pattern) throws IOException {
        return null;
    }

    @Override
    public List<Beer> getFilteredBeers(String pattern, String userName) throws IOException {
        String patternToLower = pattern.toLowerCase();
        List<Beer> list = new ArrayList<>();
        for (com.beertag.android.models.Beer Beer : getBeersByUser(userName)) {
            if (Beer.getName().toLowerCase().contains(patternToLower)) {
                list.add(Beer);
            }
        }
        return list;
    }

    @Override
    public Beer createBeer(Beer beer) throws IOException {
        return (Beer) mRepository.add(beer);
    }

    @Override
    public Beer updateBeer(Beer beer) throws IOException {
        return null;
    }


}
