package com.beertag.repositories.base;

import com.beertag.models.Tag;

import java.util.List;

public interface TagRepository {

    Tag getTagByID(int id);

    List<Tag> getAllTags();

    void create(Tag tag);

    Tag getByName(String name);

    void update(int id, Tag item);

}
