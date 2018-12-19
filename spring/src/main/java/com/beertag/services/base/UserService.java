package com.beertag.services.base;

import com.beertag.models.User;

import java.util.List;


public interface UserService {
        User getByUserName(String userName);
        List<User> getAll();
        void update(User user);
        User getUserByID(int id);

}
