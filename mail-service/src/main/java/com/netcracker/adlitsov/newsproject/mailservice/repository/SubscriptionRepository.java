package com.netcracker.adlitsov.newsproject.mailservice.repository;

import com.netcracker.adlitsov.newsproject.mailservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}