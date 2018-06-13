package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.exception.ForbiddenException;
import com.netcracker.adlitsov.newsproject.authserver.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.authserver.exception.VerificationTokenExpiredException;
import com.netcracker.adlitsov.newsproject.authserver.model.*;
import com.netcracker.adlitsov.newsproject.authserver.exception.UserAlreadyExistsException;
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
import java.util.*;

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

    public UserDetails loadUserById(int id) {
        return new UserPrincipal(getUser(id));
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

    public EmailInfo getUserEmailInfo(int userId) {
        User user = getUser(userId);

        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setEmail(user.getEmail());
        emailInfo.setConfirmed(user.isEmailConfirmed());

        return emailInfo;
    }

    @PreAuthorize("@securityService.canUpdateProfile(principal, #id)")
    public Profile updateUserProfile(int id, Profile profile) {
        User user = getUser(id);
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

        user.setRole(roleRepository.findByAuthority("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailConfirmed(false);

        Profile profile = new Profile();
        profile.setRank(rankRepository.findByName("Новичок"));
        profile.setUser(user);
        user.setProfile(profile);


        User savedUser = userRepository.save(sendConfirmation(user));
        return savedUser;
    }

    public void sendConfirmation(int userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.isEmailConfirmed()) {
            throw new ForbiddenException();
        }

        userRepository.save(sendConfirmation(user));
    }

    private User sendConfirmation(User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        user.setVerificationToken(verificationToken);

        mailService.sendConfirmationMessage(user, verificationToken);

        return user;
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

        // have no influence on user group (it's separate marker)
        user.setEmailConfirmed(true);

        // orphanRemoval - this removes verToken from table in db
        user.setVerificationToken(null);
        return userRepository.save(user);
    }

    // usual method which sends confirmation email
    @Transactional
    @PreAuthorize("@securityService.canUpdateEmail(principal, #id)")
    public String updateUserEmail(int id, String newEmail) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setEmail(newEmail);
        user.setEmailConfirmed(false);

        User updatedUser = userRepository.save(sendConfirmation(user));
        return updatedUser.getEmail();
    }

    // TODO: check password in method level security; charSequence?
    @Transactional
    @PreAuthorize("@securityService.canUpdatePassword(principal, #id, #oldPassword)")
    public void updateUserPassword(int id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // TODO: make separate method for this in  mailService
        mailService.sendSimpleMessage(user.getEmail(), "News project site - настройки аккаунта изменились",
                                      "Уважаемый " + user.getUsername() + ", кто-то изменил пароль для вашего аккаунта." +
                                              "\nЕсли это были не вы, пожалуйста, свяжитесь с администрацией сайта.");
    }

    @Transactional
    public void muteUser(int userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        final Role mutedRole = roleRepository.findByAuthority("ROLE_MUTED");

        // checking if user was already punished, then we need to save his role before prev punishment
        Role prevRole;
        Punishment punishment = user.getPunishment();
        if (punishment != null) {
            prevRole = punishment.getPrevRole();
        } else {
            punishment = new Punishment();
            prevRole = user.getRole();
        }
        punishment.setUser(user);
        punishment.setPrevRole(prevRole);
        user.setPunishment(punishment);

        user.setRole(mutedRole);
        userRepository.save(user);
    }

    public void unmuteUser(int userId) {
        clearPunishment(userId);
    }

    @Transactional
    public void banUser(int userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        final Role bannedRole = roleRepository.findByAuthority("ROLE_BANNED");

        // checking if user was already punished, then we need to save his role before prev punishment
        Role prevRole;
        Punishment punishment = user.getPunishment();
        if (punishment != null) {
            prevRole = punishment.getPrevRole();
        } else {
            punishment = new Punishment();
            prevRole = user.getRole();
        }
        punishment.setUser(user);
        punishment.setPrevRole(prevRole);
        user.setPunishment(punishment);

        user.setRole(bannedRole);
        User newUser = userRepository.save(user);
    }

    public void unbanUser(int userId) {
        clearPunishment(userId);
    }

    private void clearPunishment(int userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getPunishment() == null) {
            return;
        }

        Role prevRole = user.getPunishment().getPrevRole();
        // orphanRemoval makes this possible
        user.setPunishment(null);
        user.setRole(prevRole);

        userRepository.save(user);
    }

    public Map<Integer, AuthorCommentInfo> getAuthorsCommentInfo(List<Integer> authorsIds) {
        List<User> authors = userRepository.findUsersByIdIn(authorsIds);
        Map<Integer, AuthorCommentInfo> authorsMap = new HashMap();
        authors.forEach(a -> authorsMap.put(a.getId(), new AuthorCommentInfo(a)));
        return authorsMap;
    }
}
