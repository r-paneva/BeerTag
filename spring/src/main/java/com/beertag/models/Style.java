package com.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@EnableAutoConfiguration
@Table(name = "style")
public class Style implements Serializable {

    @Id
    @Column(name = "styleId")
    private int styleId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "style")
    @JsonIgnore
    private List<Beer> beersList;


    public Style(int id, String name) {
        this.styleId = id;
        this.name = name;
    }

    public Style() {
    }

    //Getters and setters omitted for brevity


    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public List<Beer> getBeersList() {
        return beersList;
    }

    public void setBeersList(List<Beer> beersList) {
        this.beersList = beersList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}