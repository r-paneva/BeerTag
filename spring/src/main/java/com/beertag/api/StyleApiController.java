package com.beertag.api;

import com.beertag.models.Style;
import com.beertag.services.base.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/styles")
public class StyleApiController {

    private final StyleService mStyleService;

    @Autowired
    public StyleApiController(StyleService styleService) {
        mStyleService = styleService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Style> getAll() {
        return mStyleService.getAll();
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET
    )
    public Style getStyleByID(@PathVariable("id") int id) {
        return mStyleService.getStyleByID(id);
    }

}