package com.beertag.models;


import com.beertag.Constants;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@EnableAutoConfiguration
@Table(name = "Beers")
public class Beer {

    @Id
    @GeneratedValue
    @Column(name = "BeerID")
    private int id;

    @Size(min = Constants.BEER_NAME_VALIDATION_MIN_VALUE, max = Constants.BEER_NAME_VALIDATION_MAX_VALUE)
    @Column(name = "Name" )
    private String name;

    @Column(name = "Brewery")
    private String brewery;

//    @ElementCollection(targetClass = Country.class)
//    @JoinTable(name = "country", joinColumns = @JoinColumn(name = "countryId"))
    @Column(name = "Origin", nullable = false)
//    @Enumerated(EnumType.STRING)
    private String origin;

    @Column(name = "ABV")
    private Float ABV;

    @Column(name = "Description")
    private String description;

    @Column(name = "Style")
    private String style;

    @Column(name = "Tag")
    private String tag;

    @Column(name = "Image")
    private String image;

    @Column(name = "Rating")
    private float rating;

    public Beer() {
        // public constructor is needed for parsing from/to JSON to work
    }

    public Beer(String name, String brewery, String origin, Float ABV, String description, String style, String image, String tag) {
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

    public Float getABV() {
        return ABV;
    }

    public void setABV(Float ABV) {
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
