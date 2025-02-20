package com.rsd.yaycha.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsd.yaycha.dto.UserDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        // Implement logic to create a user
        return userDTO;
    }

}
