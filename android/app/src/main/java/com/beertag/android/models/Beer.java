package com.beertag.android.models;


import java.io.Serializable;

public class Beer implements Serializable {

    private int id;
    private String name;
    private String brewery;
    private String origin;
    private String ABV;
    private String description;
    private String style;
    private String tag;
    private String image;
    private float rating;

    public Beer() {
        // public constructor is needed for parsing from/to JSON to work
    }

    public Beer( String name, String brewery, String origin, String ABV, String description, String style, String image, String tag) {
        this.name = name;
        this.brewery = brewery;
        this.origin = origin;
        this.ABV = ABV;
        this.description = description;
        this.style = style;
        this.image = image;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getABV() {
        return ABV;
    }

    public void setABV(String ABV) {
        this.ABV = ABV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
