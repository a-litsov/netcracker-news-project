package com.netcracker.adlitsov.newsproject.articlesservice.repository;

import com.netcracker.adlitsov.newsproject.articlesservice.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
