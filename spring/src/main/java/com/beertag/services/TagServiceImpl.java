package com.beertag.services;

import com.beertag.models.Beer;
import com.beertag.models.Tag;
import com.beertag.repositories.base.TagRepository;
import com.beertag.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository mTagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        mTagRepository = tagRepository;
    }


    @Override
    public List<Tag> getAll() {
        return this.mTagRepository.getAllTags();
    }

    @Override
    public Tag getTagByID(int id) {
        return mTagRepository.getTagByID(id);
    }

    @Override
    public void create(Tag tag) {
        mTagRepository.create(tag);
    }

    @Override
    public Tag getByName(String name) {
        return mTagRepository.getByName(name);
    }

    @Override
    public void update(Tag tag) {
        int id = tag.getTagId();
        mTagRepository.update(id, tag);
    }

}