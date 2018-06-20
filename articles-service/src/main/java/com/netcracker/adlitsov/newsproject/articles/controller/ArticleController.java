package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.model.*;
import com.netcracker.adlitsov.newsproject.articles.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticlesService articlesService;

    @GetMapping()
    public List<Article> getArticles() {
        return articlesService.getArticles();
    }

    @GetMapping("/preview")
    public List<ArticlePreview> getArticlesPreviews() {
        return articlesService.getArticlesPreviews();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Integer id) {
        return articlesService.getArticle(id);
    }

    @GetMapping("/{id}/preview")
    public ArticlePreview getPreview(@PathVariable("id") Integer id) {
        return articlesService.getPreview(id);
    }

    @GetMapping(params = "authorId")
    public List<ArticlePreview> getArticlesPreviewsByAuthorId(@RequestParam("authorId")
                                                                      Integer authorId) {
        return articlesService.getArticlesPreviewsByAuthorId(authorId);
    }

    @PostMapping()
    public Article addArticle(Authentication auth, @RequestBody Article article) {
        return articlesService.addArticle(auth, article);
    }


    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable(value = "id") Integer articleId,
                                 @RequestBody Article articleData) {
        return articlesService.updateArticle(articleId, articleData);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable(value = "id") Integer articleId) {
        articlesService.deleteArticleById(articleId);
    }

    @GetMapping(value = "/my-vote", params = "articleId")
    public Vote getMyVotes(@RequestParam("articleId") Integer articleId, Authentication auth) {
        return articlesService.getMyVote(articleId, auth);
    }

    @PostMapping("/{id}/like")
    public int likeComment(@PathVariable("id") int id, Authentication auth) {
        return articlesService.voteArticle(id, Vote.VoteType.LIKE, auth).getRating();
    }

    @PostMapping("/{id}/dislike")
    public int dislikeComment(@PathVariable("id") int id, Authentication auth) {
        return articlesService.voteArticle(id, Vote.VoteType.DISLIKE, auth).getRating();
    }

    @GetMapping("/{id}/exists")
    public boolean articleExists(@PathVariable("id") Integer id) {
        return articlesService.existsById(id);
    }

    @GetMapping("/day-top-mail")
    public Map<Integer, ArticleMailInfo> getTopOfTheDayMailing() {
        return articlesService.getTopOfTheDayCategorized();
    }
}
