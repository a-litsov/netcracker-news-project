package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.model.Profile;
import com.netcracker.adlitsov.newsproject.authserver.service.UserPrincipal;
import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    TokenStore tokenStore;

    @GetMapping("/profiles/{id}")
    public Profile getProfile(@PathVariable("id") int id) {
        return userService.getUserProfile(id);
    }

    // @AuthenticationPrincipal is used to get current user principal from spring security context (auth obj)
    @PutMapping("/profiles/")
    public Profile updateProfile(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Profile profile) {
        return userService.updateUserProfile(principal.getUser(), profile);
    }
}
