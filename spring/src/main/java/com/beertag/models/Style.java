package com.beertag.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@EnableAutoConfiguration
@Table(name = "style")
public class Style {

    @Id
    @Column(name = "styleId")
    private int styleId;

    @Column(name = "name")
    private String name;

    //Getters and setters omitted for brevity


    public int getCountryId() {
        return styleId;
    }

    public void setCountryId(int countryId) {
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}