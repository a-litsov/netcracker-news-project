package com.netcracker.adlitsov.newsproject.authserver.repository;

import com.netcracker.adlitsov.newsproject.authserver.model.Profile;
import com.netcracker.adlitsov.newsproject.authserver.model.Rank;
import com.netcracker.adlitsov.newsproject.authserver.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findAllByAuthorAndReceiver(Profile author, Profile receiver);
}
