package com.beertag.android.services.base;

import com.beertag.android.models.Style;

import java.io.IOException;
import java.util.List;

public interface StyleService {

    List<Style> getAllStyles() throws IOException;

    Style getById(int id) throws IOException;

    Style getByName(String name) throws IOException;

    List<Style> getFilteredStyles(String pattern, String name) throws IOException;
}
