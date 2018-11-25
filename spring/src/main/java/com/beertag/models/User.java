package com.beertag.models;

import com.beertag.Constants;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@EnableAutoConfiguration
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "UserId")
    public int id;

    @Size(min = Constants.USERNAME_VALIDATION_MIN_VALUE, max = Constants.USERNAME_VALIDATION_MAX_VALUE)
    @Column(name = "UserName")
    private String userName;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Image")
    private String imageUrl;


//    private List<Beer> drank;
//    private List<Beer> want_to_drink;

    public User() {
        // public constructor is needed for parsing from/to JSON to work
    }

    public User(int id, String userName, String firstName, String lastName, String image) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = image;
//        this.drank = new ArrayList<>();
//        this.want_to_drink = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//    public List<Beer> getDrank() {
//        return drank;
//    }
//
//    public void setDrank(List<Beer> drank) {
//        this.drank = drank;
//    }
//
//    public List<Beer> getWant_to_drink() {
//        return want_to_drink;
//    }
//
//    public void setWant_to_drink(List<Beer> want_to_drink) {
//        this.want_to_drink = want_to_drink;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


