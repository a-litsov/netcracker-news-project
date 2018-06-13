package com.netcracker.adlitsov.newsproject.comments.repository;

import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByOrderByAddDate();

    List<Comment> findByArticleId(Integer articleId);
    List<Comment> findByArticleIdOrderByAddDate(Integer articleId);
    List<Comment> findByArticleIdAndParentIsNullOrderByAddDate(Integer articleId);

    List<Comment> findByAuthorId(Integer authorId);
    List<Comment> findByParent(Comment parent);

}
