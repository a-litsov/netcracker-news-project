package com.netcracker.adlitsov.newsproject.articles.controller;

import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Article;
import com.netcracker.adlitsov.newsproject.articles.model.ArticlePreview;
import com.netcracker.adlitsov.newsproject.articles.model.Category;
import com.netcracker.adlitsov.newsproject.articles.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping()
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @PostMapping()
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable(value = "id") Integer categoryId,
                                   @Valid @RequestBody Category categoryDetails) {

        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDetails.getName());

        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/articles")
    public List<Article> getArticles(@PathVariable("id") Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return category.getArticles();
    }

    @GetMapping("/{id}/articles/preview")
    public List<ArticlePreview> getArticlesPreviews(@PathVariable("id") Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return category.getArticles()
                       .stream()
                       .map(Article::getPreview)
                       .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}/articles/preview", params = "sort=add-date")
    public List<ArticlePreview> getArticlesPreviewsSorted(@PathVariable("id") Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Comparator<ArticlePreview> comp = Comparator.comparing(ArticlePreview::getAddDate).reversed();
        return category.getArticles()
                       .stream()
                       .map(Article::getPreview)
                       .sorted(comp)
                       .collect(Collectors.toList());
    }
}
