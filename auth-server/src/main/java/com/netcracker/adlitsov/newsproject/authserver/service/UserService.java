package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.exception.ForbiddenException;
import com.netcracker.adlitsov.newsproject.authserver.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.authserver.exception.VerificationTokenExpiredException;
import com.netcracker.adlitsov.newsproject.authserver.model.Profile;
import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.exception.UserAlreadyExistsException;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationToken;
import com.netcracker.adlitsov.newsproject.authserver.repository.RankRepository;
import com.netcracker.adlitsov.newsproject.authserver.repository.RoleRepository;
import com.netcracker.adlitsov.newsproject.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserPrincipal(user);
    }

    public User getUser(int userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    public Profile getUserProfile(int userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId))
                             .getProfile();
    }

    public Profile updateUserProfile(User user, Profile profile) {
        Profile previousProfile = user.getProfile();

        previousProfile.setFirstName(profile.getFirstName());
        previousProfile.setLastName(profile.getLastName());
        previousProfile.setAvatarUrl(profile.getAvatarUrl());
        previousProfile.setAbout(profile.getAbout());
        previousProfile.setCountry(profile.getCountry());
        previousProfile.setCity(profile.getCity());
        previousProfile.setBirthDate(profile.getBirthDate());
        previousProfile.setGender(profile.getGender());

        return userRepository.save(user).getProfile();
    }

    // creates user with role_user and sends confirmation email
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user);
        }

        user.setRole(roleRepository.findByAuthority("ROLE_MUTED"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Profile profile = new Profile();
        profile.setRank(rankRepository.findByName("Новичок"));
        profile.setUser(user);
        user.setProfile(profile);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        user.setVerificationToken(verificationToken);

        User savedUser = userRepository.save(user);

        mailService.sendConfirmationMessage(savedUser, verificationToken);

        return savedUser;
    }

    // creates user with any role without confirmation email
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailConfirmed(true);

        return userRepository.save(user);
    }

    @Transactional
    public User confirmUserByToken(String token) {
        User user = userRepository.findUserByVerificationToken_Token(token)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "verificationToken", token));

        VerificationToken verificationToken = user.getVerificationToken();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            // orphanRemoval - this removes verToken from table in db
            user.setVerificationToken(null);
            userRepository.save(user);

            throw new VerificationTokenExpiredException("VerificationToken", "value", token);
        }

        // Shouldn't be used to make: banned -> user; muted -> user with already approved mail
        if (!user.isEmailConfirmed()) {
            // admin could manually change role without any mail approval, then we mustn't change role
            if ("ROLE_MUTED".equals(user.getRole().getAuthority())) {
                user.setRole(roleRepository.findByAuthority("ROLE_USER"));
            }
            user.setEmailConfirmed(true);
        }

        // orphanRemoval - this removes verToken from table in db
        user.setVerificationToken(null);
        return userRepository.save(user);
    }
}
