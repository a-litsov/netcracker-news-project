package com.netcracker.adlitsov.newsproject.articles.repository;

import com.netcracker.adlitsov.newsproject.articles.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Optional<List<Article>> findArticleByCategory(Integer articleId);
}
