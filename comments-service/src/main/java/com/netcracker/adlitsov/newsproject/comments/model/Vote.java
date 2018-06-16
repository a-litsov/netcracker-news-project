package com.netcracker.adlitsov.newsproject.comments.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"comment_id", "user_id"})
})
public class Vote {

    public static enum VoteType {
        LIKE, DISLIKE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    @JsonBackReference
    private Comment comment;

    @Enumerated(EnumType.STRING)
    VoteType type;

    public Integer getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonBackReference
    public Comment getComment() {
        return comment;
    }

    public Integer getCommentId() {
        if (comment != null) {
            return comment.getId();
        }
        return null;
    }

    @JsonBackReference
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public VoteType getType() {
        return type;
    }

    public void setType(VoteType type) {
        this.type = type;
    }
}
