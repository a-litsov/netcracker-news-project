package com.netcracker.adlitsov.newsproject.mailservice.repository;

import com.netcracker.adlitsov.newsproject.mailservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByVerificationToken_Token(String token);

    User findUserById(Integer id);
    User findUserByIdAndEmailAndSubActiveIsTrue(Integer id, String email);
}