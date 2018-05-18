package com.netcracker.adlitsov.newsproject.articles.repository;

import com.netcracker.adlitsov.newsproject.articles.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
