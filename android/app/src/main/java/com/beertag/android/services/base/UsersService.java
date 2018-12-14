package com.beertag.android.services.base;

import com.beertag.android.models.User;

import java.io.IOException;
import java.util.List;

public interface UsersService {

    List<User> getAllUsers() throws IOException;

    User getUserByUserName(String username) throws IOException;

    User updateUser(User user) throws IOException;
}
