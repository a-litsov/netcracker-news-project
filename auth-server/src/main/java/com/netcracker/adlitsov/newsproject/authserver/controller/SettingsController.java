package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.model.EmailInfo;
import com.netcracker.adlitsov.newsproject.authserver.model.PasswordsPair;
import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingsController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}/email")
    public EmailInfo getUserEmail(@PathVariable("id") int id) {
        return userService.getUserEmailInfo(id);
    }

    // TODO: add ability to change email without confirmation mail to moderators (OP_SET_EMAIL)
    @PutMapping(value = "/users/{id}/email")
    public String updateEmail(@PathVariable("id") Integer id, @RequestBody String newEmail) {
        return JSONFunctions.quote(userService.updateUserEmail(id, newEmail));
    }

    @PutMapping("/users/{id}/password")
    public void updatePassword(@PathVariable("id") Integer id, @RequestBody PasswordsPair passwords) {
        userService.updateUserPassword(id, passwords.getOldPassword(), passwords.getNewPassword());
    }
}
