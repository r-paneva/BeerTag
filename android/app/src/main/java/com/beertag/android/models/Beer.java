package com.beertag.android.models;

import java.io.Serializable;



public class Beer implements Serializable {

    private int id;
    private String name;
    private String brewery;
    private Country country;
    private String alcohol;
    private String description;
    private Style style;
    private Tag tag;
    private String image;
    private float rating;

    public Beer() {
        // public constructor is needed for parsing from/to JSON to work
    }

    public Beer( String name, String brewery, String alcohol, String description, String image, Country country, Tag tag, Style style) {
        setName(name);
        setBrewery(brewery);
        setCountry(country);
        setAlcohol(alcohol);
        setDescription(description);
        setStyle(style);
        setImage(image);
        setTag(tag);
    }

    public Beer( String name, String brewery, String alcohol, String description, Country country, Tag tag, Style style) {
        setName(name);
        setBrewery(brewery);
        setCountry(country);
        setAlcohol(alcohol);
        setDescription(description);
        setStyle(style);
        setTag(tag);
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String picture) {
        this.image = picture;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
