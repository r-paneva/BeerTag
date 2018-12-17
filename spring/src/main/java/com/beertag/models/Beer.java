package com.beertag.models;


import com.beertag.Constants;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@EnableAutoConfiguration
@Table(name = "Beers")
public class Beer implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "beerID")
    private int id;

    @Size(min = Constants.BEER_NAME_VALIDATION_MIN_VALUE, max = Constants.BEER_NAME_VALIDATION_MAX_VALUE)
    @Column(name = "name" )
    private String name;

    @Column(name = "brewery")
    private String brewery;

    @ManyToOne()
    @JoinColumn(name = "countryId", nullable=false)
    private Country country;

    @Column(name = "alcohol")
    private String alcohol;

    @Column(name = "Description")
    private String description;

    @ManyToOne()
    @JoinColumn(name = "styleId", nullable=false)
    private Style style;

    @ManyToOne()
    @JoinColumn(name = "tagId")
    private Tag tag;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "rating")
    private float rating;

    public Beer() {
        // public constructor is needed for parsing from/to JSON to work
    }

    public Beer(String name, String brewery, Country country, String alcohol, String description, Style style, String image, Tag tag) {
        setName(name);
        setBrewery(brewery);
        setCountry(country);
        setAlcohol(alcohol);
        setDescription(description);
        setStyle(style);
        setImage(image);
        setTag(tag);
    }

    public Beer(String name, String brewery, Country country, String alcohol, String description, Style style, Tag tag) {
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

    public void setStyle(Style beerStyle) {
        this.style = beerStyle;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag beerTag) {
        this.tag = beerTag;
    }

}
