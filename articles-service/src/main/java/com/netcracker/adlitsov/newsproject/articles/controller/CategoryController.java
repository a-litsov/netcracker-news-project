package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Article;
import com.netcracker.adlitsov.newsproject.articles.model.ArticleMailInfo;
import com.netcracker.adlitsov.newsproject.articles.model.ArticlePreview;
import com.netcracker.adlitsov.newsproject.articles.model.Category;
import com.netcracker.adlitsov.newsproject.articles.repository.CategoriesRepository;
import com.netcracker.adlitsov.newsproject.articles.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoriesService categoriesService;

    @GetMapping
    public List<Category> getCategories() {
        return categoriesService.getCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") Integer id) {
        return categoriesService.getCategoryById(id);
    }

    @PostMapping
    public Category addCategory(@Valid @RequestBody Category category) {
        return categoriesService.addCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Integer categoryId,
                                   @Valid @RequestBody Category categoryData) {
        return categoriesService.updateCategory(categoryId, categoryData);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(value = "id") Integer categoryId) {
        categoriesService.deleteCategory(categoryId);
    }

    @GetMapping("/{id}/articles")
    public List<Article> getCategoryArticles(@PathVariable("id") Integer categoryId) {
        return categoriesService.getCategoryArticles(categoryId);
    }

    @GetMapping("/{id}/articles/preview")
    public List<ArticlePreview> getCategoryArticlesPreviews(@PathVariable("id") Integer categoryId) {
        return categoriesService.getCategoryArticlesPreviews(categoryId);
    }

    // categoryId:articles
    @GetMapping("/all/articles/single-from-each")
    public Map<Integer, ArticleMailInfo> getMailArticles() {
        return categoriesService.getMailArticles();
    }
}
