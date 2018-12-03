package com.beertag.repositories.base;

import com.beertag.models.Style;

import java.util.List;

public interface StyleRepository {
    Style getStyleByID(int id);

    List<Style> getAllStyles();

    Style getByName(String name);

    void update(int id, Style item);

}
