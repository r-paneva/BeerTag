package com.beertag.models;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class MyBeersIdentity implements Serializable {

    @NotNull
    @Size(max = 11)
    private Integer beerId;

    @NotNull
    @Size(max = 11)
    private Integer userId;

    public MyBeersIdentity() {

    }

    public MyBeersIdentity(int beerId, int userId) {
        this.beerId = beerId;
        this.userId = userId;
    }

    public int getBeerId() {
        return beerId;
    }

    public void setBeerId(int beerId) {
        this.beerId = beerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyBeersIdentity that = (MyBeersIdentity) o;

        if (!beerId.equals(that.beerId)) return false;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        int result = beerId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}