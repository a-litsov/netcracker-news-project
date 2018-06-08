package com.netcracker.adlitsov.newsproject.authserver.repository;

import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findUserByVerificationToken_Token(String token);

}
