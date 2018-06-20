package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.model.*;
import com.netcracker.adlitsov.newsproject.authserver.exception.UserAlreadyExistsException;
import com.netcracker.adlitsov.newsproject.authserver.service.MailServiceProxy;
import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailServiceProxy mailServiceProxy;

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

    // TODO: move to service
    @GetMapping("/users/{id}/muted")
    public boolean isMuted(@PathVariable("id") Integer id) {
        return userService.getUserRole(id).getAuthority().equals("ROLE_MUTED");
    }

    @PostMapping("/users/{id}/mute")
    public Role muteUser(@PathVariable("id") Integer id) {
        return userService.muteUser(id);
    }

    @PostMapping("/users/{id}/unmute")
    public Role unmuteUser(@PathVariable("id") Integer id) {
        return userService.unmuteUser(id);
    }

    // TODO: move to service
    @GetMapping("/users/{id}/banned")
    public boolean isBanned(@PathVariable("id") Integer id) {
        return userService.getUserRole(id).getAuthority().equals("ROLE_BANNED");
    }

    @PostMapping("/users/{id}/ban")
    public Role banUser(@PathVariable("id") Integer id) {
        return userService.banUser(id);
    }

    @PostMapping("/users/{id}/unban")
    public Role unbanUser(@PathVariable("id") Integer id) {
        return userService.unbanUser(id);
    }

    @PostMapping("/users/authors-comment-info")
    public Map<Integer, AuthorCommentInfo> getAuthorsCommentInfo(@RequestBody List<Integer> authorsIds) {
        return userService.getAuthorsCommentInfo(authorsIds);
    }

    @PostMapping("/users/{id}/grant-role")
    public User setUserRole(@PathVariable("id") Integer id, @RequestBody Role role) {
        return userService.setUserRole(id, role);
    }

    @GetMapping("/users/roles")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    @GetMapping("/users/{id}/role")
    public Role getUserRole(@PathVariable("id") Integer id) {
        return userService.getUserRole(id);
    }

    @PutMapping("/users/recovery-password")
    public void recoveryPass(@RequestBody String email) {
        userService.recoveryPassword(email);
    }
}
