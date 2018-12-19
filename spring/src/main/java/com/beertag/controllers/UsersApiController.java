package com.beertag.controllers;


import com.beertag.models.User;
import com.beertag.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {
    private final UserService mUserService;

    @Autowired
    public UsersApiController(UserService userService) {
        mUserService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<User> getAll() {
        return mUserService.getAll();
    }

    @RequestMapping(
            path = "/{userName}",
            method = RequestMethod.GET
    )
    public User getUserByUsername(@PathVariable("userName") String userName) {
        return mUserService.getByUserName(userName);
    }

    @RequestMapping(
            value = "/id/{id}",
            method = RequestMethod.GET
    )
    public User getUserByID(@PathVariable("id") int id) {
        return mUserService.getUserByID(id);
    }

    @RequestMapping(
            method = RequestMethod.PUT
    )
    public void updateBeer(@RequestBody User user) {
        mUserService.update(user);
    }

}
