package com.netcracker.adlitsov.newsproject.articles.service;

import com.netcracker.adlitsov.newsproject.articles.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.articles.model.Article;
import com.netcracker.adlitsov.newsproject.articles.model.ArticleMailInfo;
import com.netcracker.adlitsov.newsproject.articles.model.ArticlePreview;
import com.netcracker.adlitsov.newsproject.articles.model.Category;
import com.netcracker.adlitsov.newsproject.articles.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    public List<Category> getCategories() {
        return categoriesRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoriesRepository.findById(id)
                                   .orElseThrow(() -> new ResourceNotFoundException("Category",
                                                                                    "id", id));
    }

    public Category addCategory(Category category) {
        return categoriesRepository.save(category);
    }

    public Category updateCategory(Integer categoryId, Category categoryData) {

        Category category = categoriesRepository.findById(categoryId)
                                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        category.setName(categoryData.getName());
        return categoriesRepository.save(category);
    }

    public void deleteCategory(Integer categoryId) {
        Category category = categoriesRepository.findById(categoryId)
                                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoriesRepository.delete(category);
    }

    public List<Article> getCategoryArticles(@PathVariable("id") Integer categoryId) {
        Category category = categoriesRepository.findById(categoryId)
                                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return category.getArticles();
    }

    /*
     * Returns articles previews list sorted by article's creation date in descending order
     */
    public List<ArticlePreview> getCategoryArticlesPreviews(@PathVariable("id") Integer categoryId) {
        Category category = categoriesRepository.findById(categoryId)
                                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Comparator<ArticlePreview> comp = Comparator.comparing(ArticlePreview::getAddDate).reversed();
        return category.getArticles()
                       .stream()
                       .map(Article::getPreview)
                       .sorted(comp)
                       .collect(Collectors.toList());
    }
}
