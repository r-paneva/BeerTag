package com.beertag.android.services.base;

import com.beertag.android.models.Tag;

import java.io.IOException;
import java.util.List;

public interface TagService {


    List<Tag> getAllTags() throws IOException;

    Tag getById(int id) throws IOException;

    Tag getByName(String name) throws IOException;

    List<Tag> getFilteredTags(String pattern, String name) throws IOException;

}
