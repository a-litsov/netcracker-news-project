package com.netcracker.adlitsov.newsproject.articles.repository;

import com.netcracker.adlitsov.newsproject.articles.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
//@PreAuthorize("#oauth2.hasScope('ARTICLE')") //need to move that in service layer (to do)
public interface CategoriesRepository extends JpaRepository<Category, Integer> {
}
