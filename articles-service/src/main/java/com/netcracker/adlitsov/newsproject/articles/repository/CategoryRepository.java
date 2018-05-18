package com.netcracker.adlitsov.newsproject.articles.repository;

import com.netcracker.adlitsov.newsproject.articles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
