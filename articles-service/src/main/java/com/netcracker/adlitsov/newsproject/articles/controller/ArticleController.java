package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Article;
import com.netcracker.adlitsov.newsproject.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping()
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable("id") Integer id) {
        return articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
    }

    @PostMapping()
    public Article createArticle(@Valid @RequestBody Article article) {
        return articleRepository.save(article);
    }



    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable(value = "id") Integer articleId,
                                 @Valid @RequestBody Article articleDetails) {

        Article article = articleRepository.findById(articleId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setCategory(articleDetails.getCategory());


        Article updatedArticle = articleRepository.save(article);
        return updatedArticle;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable(value = "id") Integer articleId) {
        Article article = articleRepository.findById(articleId)
                                  .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        articleRepository.delete(article);

        return ResponseEntity.ok().build();
    }
}
