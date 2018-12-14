package com.beertag.repositories.base;

import com.beertag.models.User;

import java.util.List;

public interface UsersRepository{
    User getByUserName(String userName);

    List getAll();

    void update (User user);
}
