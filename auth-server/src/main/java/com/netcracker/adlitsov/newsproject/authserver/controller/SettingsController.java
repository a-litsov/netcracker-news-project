package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.model.PasswordsPair;
import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingsController {

    @Autowired
    UserService userService;

    @PutMapping("/user/{id}/email")
    public String updateEmail(@PathVariable("id") Integer id, @RequestBody String newEmail) {
        return userService.updateUserEmail(id, newEmail);
    }

    @PutMapping("/user/{id}/password")
    public @ResponseBody void updatePassword(@PathVariable("id") Integer id, @RequestBody PasswordsPair passwords) {
        userService.updateUserPassword(id, passwords.getOldPassword(), passwords.getNewPassword());
    }
}
