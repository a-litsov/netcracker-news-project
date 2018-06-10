package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Article;
import com.netcracker.adlitsov.newsproject.articles.model.ArticlePreview;
import com.netcracker.adlitsov.newsproject.articles.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping()
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/preview")
    public List<ArticlePreview> getAllPreviews() {
        return articleRepository.findAll()
                                .stream()
                                .map(Article::getPreview)
                                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Integer id) {
        return articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
    }

    @GetMapping("/{id}/preview")
    public ArticlePreview getPreview(@PathVariable("id") Integer id) {
        return articleRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id))
                                .getPreview();

    }

    @GetMapping(params = "authorId")
    public List<ArticlePreview> getArticlesByAuthorId(@RequestParam("authorId") Integer authorId) {
        return articleRepository.findArticleByAuthorId(authorId)
                                .orElseThrow(() -> new ResourceNotFoundException("Articles", "authorId", authorId))
                                .stream()
                                .map(Article::getPreview)
                                .collect(Collectors.toList());
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
        article.setAuthorId(articleDetails.getAuthorId());
        article.setCategory(articleDetails.getCategory());
        article.setTag(articleDetails.getTag());
        article.setLogoSrc(articleDetails.getLogoSrc());
        article.setContent(articleDetails.getContent());

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
