package com.netcracker.adlitsov.newsproject.comments.controller;

import com.netcracker.adlitsov.newsproject.comments.config.ArticlesServiceClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: gateway
@FeignClient(name = "articles-service", url="http://localhost:8084",
        configuration = ArticlesServiceClientConfiguration.class)
public interface ArticlesServiceProxy {

    @GetMapping("articles/{id}/exists")
    public boolean articleExists(@PathVariable("id") Integer id);
}