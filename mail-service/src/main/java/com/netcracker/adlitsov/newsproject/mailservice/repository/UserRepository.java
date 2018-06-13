package com.netcracker.adlitsov.newsproject.mailservice.repository;

import com.netcracker.adlitsov.newsproject.mailservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}