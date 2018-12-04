package com.beertag.services;

import com.beertag.models.Style;
import com.beertag.repositories.base.StyleRepository;
import com.beertag.services.base.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StyleServiceImpl implements StyleService {

    private final StyleRepository mStyleRepository;


    @Autowired
    public StyleServiceImpl(
            StyleRepository styleRepository) {
        mStyleRepository = styleRepository;
    }


    @Override
    public List<Style> getAll() {
        return this.mStyleRepository.getAllStyles();
    }

    @Override
    public Style getStyleByID(int id) {
        return mStyleRepository.getStyleByID(id);
    }

    @Override
    public Style getByName(String name) {
        return mStyleRepository.getByName(name);
    }

    @Override
    public void update(Style style) {
        int id = style.getId();
        mStyleRepository.update(id, style);
    }
}
