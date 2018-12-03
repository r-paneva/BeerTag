package com.beertag.android.models;

import java.io.Serializable;

public final class Country implements Serializable {
    private long id;
    private String name;

    public Country() {
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
