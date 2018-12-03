package com.beertag.android.services;

import com.beertag.android.models.Style;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.base.StyleService;

import java.io.IOException;
import java.util.List;

public class HttpStyleService implements StyleService {
    private final Repository<Style> mRepository;

    public HttpStyleService(Repository<Style> mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public List<Style> getAllStyles() throws IOException {
        return mRepository.getAll();
    }

    @Override
    public Style getById(int id) throws IOException {
        return mRepository.getById(id);
    }

    @Override
    public Style getByName(String name) throws IOException {
        return mRepository.getByName(name);
    }

    @Override
    public List<Style> getFilteredStyles(String pattern, String name) throws IOException {
        return null;
    }
}
