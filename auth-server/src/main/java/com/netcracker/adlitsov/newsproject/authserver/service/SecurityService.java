package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.exception.ForbiddenException;
import com.netcracker.adlitsov.newsproject.authserver.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.authserver.model.Profile;
import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.model.Vote;
import com.netcracker.adlitsov.newsproject.authserver.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {

    @Autowired
    UserService userService;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean canUpdateProfile(UserPrincipal principal, int profileId) {
        return principal.getId() == profileId || principal.hasAuthority("OP_UPDATE_PROFILE");
    }

    public boolean canUpdatePassword(UserPrincipal principal, int userId, String oldPassword) {
        if (principal.hasAuthority("OP_UPDATE_PASSWORD")) {
            return true;
        }

        // user changes his own password
        if (principal.getId() == userId) {
            User user = userService.getUser(userId);

            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean canUpdateEmail(UserPrincipal principal, int profileId) {
        return principal.getId() == profileId || principal.hasAuthority("OP_UPDATE_EMAIL");
    }

    public boolean canVote(UserPrincipal principal, int profileId) {
        Profile authorProfile = userService.getUser(principal.getId()).getProfile();
        Profile receiverProfile = userService.getUser(profileId).getProfile();

        List<Vote> votes = voteRepository.findAllByAuthorAndReceiver(authorProfile, receiverProfile);
        return votes.isEmpty() && principal.hasAuthority("OP_VOTE_PROFILE");
    }
}
