package com.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@EnableAutoConfiguration
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private List<Beer> beersList;

//    Getters and setters omitted for brevity


    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name){this.name = name;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Beer> getBeersList() {
        return beersList;
    }

    public void setBeersList(List<Beer> beersList) {
        this.beersList = beersList;
    }
}
