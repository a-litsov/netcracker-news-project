package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.domain.User;
import com.netcracker.adlitsov.newsproject.authserver.exception.UserAlreadyExistsException;
import com.netcracker.adlitsov.newsproject.authserver.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println("USER:" + user);
        User createdUser = null;
        try {
            createdUser = myUserDetailsService.registerUser(user);
        } catch(UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("USER:" + user);
        User createdUser = null;
        try {
            createdUser = myUserDetailsService.createUser(user);
        } catch(UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        System.out.println("Created user:" + createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
