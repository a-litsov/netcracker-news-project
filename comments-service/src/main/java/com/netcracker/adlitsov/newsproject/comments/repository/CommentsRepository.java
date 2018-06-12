package com.netcracker.adlitsov.newsproject.comments.repository;

import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByOrderByAddDate();

    Optional<List<Comment>> findByArticleIdOrderByAddDate(Integer articleId);
    Optional<List<Comment>> findByArticleIdAndParentIsNullOrderByAddDate(Integer articleId);

    Optional<List<Comment>> findByAuthorId(Integer authorId);
    List<Comment> findByParent(Comment parent);

}
