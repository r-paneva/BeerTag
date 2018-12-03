package com.beertag.services.base;

import com.beertag.models.Style;

import java.util.List;

public interface StyleService {


    List<Style> getAll();
    Style getStyleByID(int id);
    Style getByName(String name);
    void update(Style style);

}
