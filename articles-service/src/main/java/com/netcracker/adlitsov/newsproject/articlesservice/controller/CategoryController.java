package com.netcracker.adlitsov.newsproject.articlesservice.controller;

import com.netcracker.adlitsov.newsproject.articlesservice.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articlesservice.model.Article;
import com.netcracker.adlitsov.newsproject.articlesservice.model.Category;
import com.netcracker.adlitsov.newsproject.articlesservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
}
