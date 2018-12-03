package com.beertag.android.services;

import com.beertag.android.models.User;
import com.beertag.android.repositories.base.Repository;
import com.beertag.android.services.base.UsersService;

import java.io.IOException;
import java.util.List;


public class HttpUsersService implements UsersService {
    private final Repository<User> mUsersRepository;

    public HttpUsersService(Repository<User> UsersRepository) {
        this.mUsersRepository = UsersRepository;
    }

    @Override
    public List<User> getAllUsers() throws IOException {
        return mUsersRepository.getAll();
    }

    @Override
    public User getUserByUsername(String username) throws IOException {
//        return mUsersRepository.getUserByUsername(username);
        return null;
    }
}