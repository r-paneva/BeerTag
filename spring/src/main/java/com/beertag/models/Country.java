package com.beertag.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@EnableAutoConfiguration
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

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

}