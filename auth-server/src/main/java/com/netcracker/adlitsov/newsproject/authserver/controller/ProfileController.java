package com.netcracker.adlitsov.newsproject.authserver.controller;

import com.netcracker.adlitsov.newsproject.authserver.converter.GenderConverter;
import com.netcracker.adlitsov.newsproject.authserver.model.Profile;
import com.netcracker.adlitsov.newsproject.authserver.service.UserPrincipal;
import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @PutMapping("/profiles/{id}")
    public Profile updateUserProfile(@PathVariable("id") Integer id, @RequestBody Profile profile) {
        return userService.updateUserProfile(id, profile);
    }

    // @AuthenticationPrincipal is used to get current user principal from spring security context (auth obj)
    @GetMapping("/genders")
    public List<String> getGenders() {
        GenderConverter genderConverter = new GenderConverter();
        return Arrays.stream(Profile.Gender.values())
                     .map(genderConverter::convertToDatabaseColumn)
                     .collect(Collectors.toList());
    }
}
