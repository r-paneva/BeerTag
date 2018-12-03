package com.beertag.android.services;

import com.beertag.android.models.Tag;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.base.TagService;

import java.io.IOException;
import java.util.List;

public class HttpTagService implements TagService {
    private final Repository<Tag> mRepository;

    public HttpTagService(Repository<Tag> mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public List<Tag> getAllTags() throws IOException {
        return mRepository.getAll();
    }

    @Override
    public Tag getById(int id) throws IOException {
        return mRepository.getById(id);
    }

    @Override
    public Tag getByName(String name) throws IOException {
        return mRepository.getByName(name);
    }

    @Override
    public List<Tag> getFilteredTags(String pattern, String name) throws IOException {
        return null;
    }
}
