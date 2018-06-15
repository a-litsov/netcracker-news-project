package com.netcracker.adlitsov.newsproject.comments.repository;

import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import com.netcracker.adlitsov.newsproject.comments.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotesRepository extends JpaRepository<Vote, Integer> {

}