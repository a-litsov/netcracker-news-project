package com.netcracker.adlitsov.newsproject.comments.controller;

import com.netcracker.adlitsov.newsproject.comments.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import com.netcracker.adlitsov.newsproject.comments.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping()
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    @PostMapping()
    public Comment createComment(@Valid @RequestBody Comment comment) {
        return commentRepository.save(comment);
    }



    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable(value = "id") Integer commentId,
                                 @Valid @RequestBody Comment commentDetails) {

        Comment comment = commentRepository.findById(commentId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setArticleId(commentDetails.getArticleId());
        comment.setAuthorId(commentDetails.getAuthorId());
        comment.setParent(commentDetails.getParent());
        comment.setContent(commentDetails.getContent());


        Comment updatedComment = commentRepository.save(comment);
        return updatedComment;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                                           .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.delete(comment);

        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "articleId")
    public List<Comment> getCommentsByArticleId(@RequestParam("articleId") Integer articleId) {
        return commentRepository.findByArticleId(articleId).orElseThrow(() -> new ResourceNotFoundException("Comment", "articleId", articleId));
    }

    @GetMapping(params = {"articleId", "root"})
    public List<Comment> getRootCommentsByArticleId(@RequestParam("articleId") Integer articleId) {
        return commentRepository.findByArticleIdAndParentIsNull(articleId).orElseThrow(() -> new ResourceNotFoundException("Comment", "articleId", articleId));
    }

    @GetMapping(params = "authorId")
    public List<Comment> getCommentsByAuthorId(@RequestParam("authorId") Integer authorId) {
        return commentRepository.findByAuthorId(authorId).orElseThrow(() -> new ResourceNotFoundException("Comment", "authorId", authorId));
    }
}