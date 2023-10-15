package com.samia.ecole.controllers;

import com.samia.ecole.entities.User;
import com.samia.ecole.services.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    public User saveUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        return savedUser;
    }
}
