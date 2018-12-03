package com.beertag.services.base;

import com.beertag.models.Beer;

import java.util.List;

public interface BeerService {

        List<Beer> getAll();
        Beer getBeerByID(int id);
        void update(Beer beer);
        void create(Beer beer);
        void delete(Beer beer);
}
