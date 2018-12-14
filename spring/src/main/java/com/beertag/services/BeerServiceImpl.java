package com.beertag.services;

import com.beertag.models.Beer;
import com.beertag.repositories.base.BeerRepository;
import com.beertag.services.base.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository mBeerRepository;


    @Autowired
    public BeerServiceImpl(
            BeerRepository beerRepository) {
        mBeerRepository = beerRepository;
    }

    @Override
    public List<Beer> getAll() {
        return this.mBeerRepository.getAll();
    }


    @Override
    public Beer getBeerByID(int id) {
        return mBeerRepository.getBeerByID(id);
    }

    @Override
    public void update(Beer beer) {
        mBeerRepository.update(beer);
    }

    @Override
    public void create(Beer beer) {
        mBeerRepository.create(beer);
    }

    @Override
    public void delete(Beer beer) {
        mBeerRepository.delete(beer);
    }
}
