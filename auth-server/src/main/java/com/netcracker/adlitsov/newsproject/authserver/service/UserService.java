package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.model.Rank;
import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.exception.UserAlreadyExistsException;
import com.netcracker.adlitsov.newsproject.authserver.model.UserInfo;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationToken;
import com.netcracker.adlitsov.newsproject.authserver.repository.RankRepository;
import com.netcracker.adlitsov.newsproject.authserver.repository.RoleRepository;
import com.netcracker.adlitsov.newsproject.authserver.repository.UserRepository;
import com.netcracker.adlitsov.newsproject.authserver.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
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
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }

    // creates user with role_user
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user);
        }

        user.setRole(roleRepository.findByAuthority("ROLE_MUTED"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserInfo userInfo = new UserInfo();
        userInfo.setAvatarUrl("https://cdn3.iconfinder.com/data/icons/pictofoundry-pro-vector-set/512/Avatar-512.png");
        userInfo.setAbout("Этот пользователь предпочёл пока не указывать информации о себе");
        Date currentDate = new Date();
        userInfo.setLastOnline(currentDate);
        userInfo.setRegDate(currentDate);
        userInfo.setRank(rankRepository.findByName("Новичок"));
        userInfo.setUser(user);
        user.setUserInfo(userInfo);

        User savedUser = userRepository.save(user);

        VerificationToken verificationToken = createVerificationToken(savedUser);
        mailService.sendConfirmationMessage(savedUser, verificationToken);

        return savedUser;
    }

    // creates user with any role
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUser(user);

        return tokenRepository.save(verificationToken);
    }

    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    public void removeVerificationToken(String verificationToken) {
        tokenRepository.deleteVerificationTokenByToken(verificationToken);
    }

    @Transactional
    public boolean confirmUserByToken(String token) {
        VerificationToken verificationToken = getVerificationToken(token);
        if (verificationToken == null) {
            return false;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return false;
        }

        User storedUser = userRepository.findById(user.getId())
                                        .orElseThrow(() -> new IllegalArgumentException("User doesn't exist!"));
        // Shouldn't be used to make: banned -> user; muted -> user with already approved mail
        if (!storedUser.isEmailConfirmed()) {
            // admin could manually change role without any mail approval, then we mustn't change role
            if ("ROLE_MUTED".equals(storedUser.getRole().getAuthority())) {
                storedUser.setRole(roleRepository.findByAuthority("ROLE_USER"));
            }
            storedUser.setEmailConfirmed(true);
            userRepository.save(storedUser);
        }
        removeVerificationToken(token);

        return true;
    }
}
