package com.beertag.android.services.base;


import com.beertag.android.models.Beer;

import java.io.IOException;
import java.util.List;

public interface BeersService {
    List<Beer> getAllBeers() throws IOException;

    List<Beer> getBeersByStyle(String userName) throws IOException;

    List<Beer> getBeersByUser(String userName) throws IOException;

    Beer getDetailsById(int id) throws IOException;

    List<Beer> getFilteredBeers(String pattern) throws IOException;

    List<Beer> getFilteredBeers(String pattern, String userName) throws IOException;

    Beer createBeer(Beer beer) throws IOException;

    Beer updateBeer(Beer beer) throws IOException;
}
