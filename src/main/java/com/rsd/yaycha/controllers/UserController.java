package com.rsd.yaycha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsd.yaycha.dto.UserDTO;
import com.rsd.yaycha.dto.UserWithTokenDto;
import com.rsd.yaycha.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserWithTokenDto> createUser(@RequestBody UserDTO userDTO) {
        UserWithTokenDto createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserWithTokenDto> loginUser(@RequestBody UserDTO userDTO) {
        UserWithTokenDto loginUser = userService.loginUser(userDTO);
        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("User logged out successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable int id) {
        UserDTO deletedUser = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }

}
