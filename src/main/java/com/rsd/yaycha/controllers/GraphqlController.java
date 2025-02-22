package com.rsd.yaycha.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.services.UserService;

@Controller
public class GraphqlController {

    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @QueryMapping
    public User userWithId(@Argument int id) {
        return userService.findOneById(id);
    }

}
