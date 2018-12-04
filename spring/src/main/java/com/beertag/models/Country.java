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
    @Column(name = "id")
    private int id;

//    @OneToMany(mappedBy = "country")
//    @JsonIgnore
//    private List<Beer> beersList;


    @Column( name = "name")
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country() {
    }

    //Getters and setters omitted for brevity


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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