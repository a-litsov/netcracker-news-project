package com.netcracker.adlitsov.newsproject.articles.repository;

import com.netcracker.adlitsov.newsproject.articles.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@PreAuthorize("#oauth2.hasScope('ARTICLE')") //need to move that in service layer (to do)
public interface ArticlesRepository extends JpaRepository<Article, Integer> {
    List<Article> findArticleByCategory(Integer articleId);

    List<Article> findArticlesByAuthorId(Integer articleId);

    List<Article> findArticlesByCategoryIdAndTitleContainingIgnoreCaseOrderByAddDateDesc(Integer categoryId, String search);

    boolean existsById(Integer id);
}
