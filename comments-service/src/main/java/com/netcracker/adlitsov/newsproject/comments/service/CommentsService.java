package com.netcracker.adlitsov.newsproject.comments.service;

import com.netcracker.adlitsov.newsproject.comments.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import com.netcracker.adlitsov.newsproject.comments.model.Vote;
import com.netcracker.adlitsov.newsproject.comments.repository.CommentsRepository;
import com.netcracker.adlitsov.newsproject.comments.repository.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    VotesRepository votesRepository;


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

    public List<Integer> getAuthorsIdByArticleId(int articleId) {
        List<Comment> comments = commentsRepository.findByArticleId(articleId);
        return comments.stream().map(c -> c.getAuthorId()).distinct().collect(Collectors.toList());
    }

    @Transactional
    public Comment voteComment(int id, Vote.VoteType type, Authentication auth) {
        Comment comment = commentsRepository.findById(id)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        int userId = getUserId(auth);

        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setType(type);
        vote.setComment(comment);

        Vote foundVote = comment.getVoteFromUser(userId);
        if (foundVote != null) {
            comment.deleteVote(foundVote);
            if (foundVote.getType().equals(vote.getType())) {
                return commentsRepository.save(comment);
            }
        }

        comment.addVote(vote);
        return commentsRepository.saveAndFlush(comment);
    }

    public Map<Integer, Vote> getMyVotes(int articleId, Authentication auth) {
        List<Comment> comments = getCommentsByArticleId(articleId);
        Map<Integer, Vote> votes = new HashMap<>();
        for(Comment comment: comments) {
            Vote v = comment.getVoteFromUser(getUserId(auth));
            if (v != null) {
                votes.put(v.getCommentId(), v);
            }
        }
        return votes;
    }

    private int getUserId(Authentication auth) {
        Map<String, Object> details = (Map<String, Object>)((OAuth2AuthenticationDetails)auth.getDetails()).getDecodedDetails();
        return (int)details.get("user_id");
    }

    @Transactional
    public void deleteCommentByArticleId(int articleId) {
        commentsRepository.deleteAllByArticleId(articleId);
    }
}
