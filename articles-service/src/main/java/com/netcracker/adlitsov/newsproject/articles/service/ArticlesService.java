package com.netcracker.adlitsov.newsproject.articles.service;

import com.netcracker.adlitsov.newsproject.articles.controller.CommentsServiceProxy;
import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Article;
import com.netcracker.adlitsov.newsproject.articles.model.ArticlePreview;
import com.netcracker.adlitsov.newsproject.articles.model.Vote;
import com.netcracker.adlitsov.newsproject.articles.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticlesService {

    @Autowired
    ArticlesRepository articlesRepository;

    @Autowired
    CommentsServiceProxy commentsServiceProxy;

    public List<Article> getArticles() {
        return articlesRepository.findAll();
    }

    public List<ArticlePreview> getArticlesPreviews() {
        return articlesRepository.findAll()
                                 .stream()
                                 .map(Article::getPreview)
                                 .collect(Collectors.toList());
    }

    public Article getArticle(Integer id) {
        return articlesRepository.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
    }

    public ArticlePreview getPreview(Integer id) {
        return articlesRepository.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id))
                                 .getPreview();
    }

    public List<ArticlePreview> getArticlesPreviewsByAuthorId(Integer authorId) {
        return articlesRepository.findArticlesByAuthorId(authorId)
                                 .stream()
                                 .map(Article::getPreview)
                                 .collect(Collectors.toList());
    }

    public Article addArticle(Authentication auth, Article article) {
        int userId = getUserId(auth);
        article.setAuthorId(userId);

        return articlesRepository.save(article);
    }

    public Article updateArticle(Integer articleId, Article articleData) {
        Article article = articlesRepository.findById(articleId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        article.setTitle(articleData.getTitle());
        article.setAuthorId(articleData.getAuthorId());
        article.setCategory(articleData.getCategory());
        article.setTag(articleData.getTag());
        article.setLogoSrc(articleData.getLogoSrc());
        article.setContent(articleData.getContent());

        return articlesRepository.save(article);
    }

    @Transactional
    public void deleteArticleById(Integer articleId) {
        commentsServiceProxy.deleteCommentsByArticleId(articleId);

        Article article = articlesRepository.findById(articleId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        articlesRepository.delete(article);
    }

    @Transactional
    public Article voteArticle(int id, Vote.VoteType type, Authentication auth) {
        Article article = articlesRepository.findById(id)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        int userId = getUserId(auth);

        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setType(type);
        vote.setArticle(article);

        Vote foundVote = article.getVoteFromUser(userId);
        if (foundVote != null) {
            article.deleteVote(foundVote);
            if (foundVote.getType().equals(vote.getType())) {
                return articlesRepository.save(article);
            }
        }

        article.addVote(vote);
        return articlesRepository.saveAndFlush(article);
    }

    public Vote getMyVote(int articleId, Authentication auth) {
        Article article = articlesRepository.findById(articleId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        return article.getVoteFromUser(getUserId(auth));
    }

    private int getUserId(Authentication auth) {
        Map<String, Object> details = (Map<String, Object>)((OAuth2AuthenticationDetails)auth.getDetails()).getDecodedDetails();
        return (int)details.get("user_id");
    }

    public List<Article> searchArticles(Integer categoryId, String search) {
        return articlesRepository.findArticlesByCategoryIdAndTitleContainingIgnoreCaseOrderByAddDateDesc(categoryId, search);
    }

    public boolean existsById(int id) {
        return articlesRepository.existsById(id);
    }
}