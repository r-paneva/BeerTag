package com.beertag.controllers;

import com.beertag.models.Tag;
import com.beertag.services.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsApiController {

    private final TagService mTagService;

    @Autowired
    public TagsApiController(TagService tagService) {
        mTagService = tagService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Tag> getAll() {
        return mTagService.getAll();
    }

    @RequestMapping(
            value = "/id/{id}",
            method = RequestMethod.GET
    )
    public Tag getTagByID(@PathVariable("id") int id) {
        return mTagService.getTagByID(id);
    }

    @RequestMapping(
            method = RequestMethod.POST
    )
    public void createTag(@RequestBody Tag tag) {
        mTagService.create(tag);
    }

    @RequestMapping(
            path = "/{name}",
            method = RequestMethod.GET
    )
    public Tag getByName(@PathVariable("name") String name) {
        return mTagService.getByName(name);
    }

}
