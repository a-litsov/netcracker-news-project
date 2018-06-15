package com.netcracker.adlitsov.newsproject.mailservice.controller;

import com.netcracker.adlitsov.newsproject.mailservice.model.ArticleMailInfo;
import com.netcracker.adlitsov.newsproject.mailservice.model.ArticlePreview;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

// TODO: gateway
@FeignClient(name = "articles-service", url="${spring.gateway.url}")
public interface ArticlesServiceProxy {

    @GetMapping("/articles/mail")
    public Map<Integer, ArticleMailInfo> getAllMailArticles();

}