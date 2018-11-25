package com.beertag.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Entity
@EnableAutoConfiguration
@Table(name = "Beers")

public class Country {

    @Id
    @Column(name = "countryId")
    private Long countryId;

    @Column(columnDefinition = "beer_style_name", name = "name")
    private String name;

    //Getters and setters omitted for brevity


    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}