package com.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@EnableAutoConfiguration
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @Column(name = "countryId")
    private int countryId;

//    @OneToMany(mappedBy = "country")
//    @JsonIgnore
//    private List<Beer> beersList;


    @Column( name = "name")
    private String name;

    public Country(int countryId, String name) {
        this.countryId = countryId;
        this.name = name;
    }

    public Country() {
    }

    //Getters and setters omitted for brevity


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Beer> getBeersList() {
//        return beersList;
//    }
//
//    public void setBeersList(List<Beer> beersList) {
//        this.beersList = beersList;
//    }
}