package com.beertag.services;

import com.beertag.models.User;
import com.beertag.repositories.base.UsersRepository;
import com.beertag.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository mUsersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        mUsersRepository = usersRepository;
    }

    @Override
    public List<User> getAll() {
        return this.mUsersRepository.getAll();
    }

    @Override
    public User getByUserName(String userName) {
        return mUsersRepository.getByUserName(userName);
    }

}
