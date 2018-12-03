package com.beertag.services.base;

import com.beertag.models.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAll();
    Tag getTagByID(int id);
    void create(Tag tag);
    Tag getByName(String name);
    void update(Tag tag);
}
