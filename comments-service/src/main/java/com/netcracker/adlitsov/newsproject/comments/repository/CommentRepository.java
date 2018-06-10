package com.netcracker.adlitsov.newsproject.comments.repository;

import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<List<Comment>> findByArticleId(Integer articleId);
    Optional<List<Comment>> findByArticleIdAndParentIsNull(Integer articleId);

    Optional<List<Comment>> findByAuthorId(Integer authorId);

}
