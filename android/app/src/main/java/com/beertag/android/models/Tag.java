package com.beertag.android.models;

import java.io.Serializable;

public class Tag implements Serializable {

    private long id;
    private String name;

    public Tag()  {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
