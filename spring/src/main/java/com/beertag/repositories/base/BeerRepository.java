package com.beertag.repositories.base;

import com.beertag.models.Beer;

import java.util.List;

public interface BeerRepository {

    Beer getBeerByID(int id);

    void create(Beer beer);

    void update(int id, Beer beer);

    void delete(Beer beer);

    List getAll();
}
