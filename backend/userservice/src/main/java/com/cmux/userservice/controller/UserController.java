package com.cmux.userservice.controller;

import com.cmux.userservice.service.Userservice;
import com.cmux.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Userservice userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.register(user);
    }

}
