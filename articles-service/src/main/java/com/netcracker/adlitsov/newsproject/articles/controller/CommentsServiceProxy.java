package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.config.CommentsServiceClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// TODO: gateway
@FeignClient(name = "comments-service", url="http://localhost:8084", configuration = CommentsServiceClientConfiguration.class)
public interface CommentsServiceProxy {

    @DeleteMapping("comments/article/{id}")
    public void deleteCommentsByArticleId(@PathVariable(value = "id") Integer articleId);
}