package com.netcracker.adlitsov.newsproject.comments.service;

import com.netcracker.adlitsov.newsproject.comments.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import com.netcracker.adlitsov.newsproject.comments.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Service
public class CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    public List<Comment> getAllComments() {
        return commentsRepository.findAllByOrderByAddDate();
    }

    public Comment getCommentById(Integer id) {
        return commentsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    public Comment createComment(Comment comment) {
        return commentsRepository.save(comment);
    }

    public Comment updateComment(Integer commentId, Comment commentDetails) {

        Comment comment = commentsRepository.findById(commentId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setArticleId(commentDetails.getArticleId());
        comment.setAuthorId(commentDetails.getAuthorId());
        comment.setParent(commentDetails.getParent());
        comment.setContent(commentDetails.getContent());

        return commentsRepository.save(comment);
    }

    public Comment hideComment(Integer commentId) {

        Comment comment = commentsRepository.findById(commentId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setHidden(true);

        return commentsRepository.save(comment);
    }

    public Comment showComment(Integer commentId) {

        Comment comment = commentsRepository.findById(commentId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setHidden(false);

        return commentsRepository.save(comment);
    }

    public void deleteComment(@PathVariable(value = "id") Integer commentId) {
        Comment comment = commentsRepository.findById(commentId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // made all children to move one level higher
        List<Comment> children = commentsRepository.findByParent(comment);
        children.forEach((c) -> c.setParent(comment.getParent()));
        commentsRepository.saveAll(children);
        commentsRepository.delete(comment);
    }

    public List<Comment> getCommentsByArticleId(Integer articleId) {
        return commentsRepository.findByArticleIdOrderByAddDate(articleId);
    }

    public List<Comment> getRootCommentsByArticleId(Integer articleId) {
        return commentsRepository.findByArticleIdAndParentIsNullOrderByAddDate(articleId);
    }

    public List<Comment> getCommentsByAuthorId(Integer authorId) {
        return commentsRepository.findByAuthorId(authorId);
    }
}
