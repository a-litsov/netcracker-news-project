package com.netcracker.adlitsov.newsproject.articlesservice.repository;

import com.netcracker.adlitsov.newsproject.articlesservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
