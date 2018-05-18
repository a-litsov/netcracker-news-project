package com.netcracker.adlitsov.newsproject.articlesservice.repository;

import com.netcracker.adlitsov.newsproject.articlesservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
