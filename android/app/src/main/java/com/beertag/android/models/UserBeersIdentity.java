package com.beertag.android.models;

import java.io.Serializable;

public class UserBeersIdentity implements Serializable {

    private Integer beerId;
    private Integer userId;

    public UserBeersIdentity() {

    }

    public UserBeersIdentity(int beerId, int userId) {
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

        UserBeersIdentity that = (UserBeersIdentity) o;

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