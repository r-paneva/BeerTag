package com.beertag.controllers;

import com.beertag.models.Style;
import com.beertag.services.base.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/styles")
public class StylesApiController {

    private final StyleService mStyleService;

    @Autowired
    public StylesApiController(StyleService styleService) {
        mStyleService = styleService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Style> getAll() {
        return mStyleService.getAll();
    }

    @RequestMapping(
            value = "/id/{id}",
            method = RequestMethod.GET
    )
    public Style getStyleByID(@PathVariable("id") int id) {
        return mStyleService.getStyleByID(id);
    }

    @RequestMapping(
            path = "/{name}",
            method = RequestMethod.GET
    )
    public Style getByName(@PathVariable("name") String name) {
        return mStyleService.getByName(name);
    }

}