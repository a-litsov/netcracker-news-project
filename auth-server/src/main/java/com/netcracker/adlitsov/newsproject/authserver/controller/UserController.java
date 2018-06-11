package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.model.EmailInfo;
import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.exception.UserAlreadyExistsException;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationToken;
import com.netcracker.adlitsov.newsproject.authserver.service.MailService;
import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    // sends verification email
    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println("USER:" + user);
        User createdUser = null;
        try {
            createdUser = userService.registerUser(user);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // does not need any email approval and used only by admin
    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("USER:" + user);
        User createdUser = null;
        try {
            createdUser = userService.createUser(user);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        System.out.println("Created user:" + createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}/send-confirmation")
    public void sendMessage(@PathVariable("id") Integer id) {
        userService.sendConfirmation(id);
    }

    @GetMapping(value = "/users/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        User confirmedUser = userService.confirmUserByToken(token);
        if (confirmedUser != null) {
            return new ResponseEntity<>("Successfully verified email your email, " + confirmedUser.getUsername() + "!",
                                        HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Confirmation not succeedded", HttpStatus.BAD_REQUEST);
        }
    }
}
