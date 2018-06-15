package com.netcracker.adlitsov.newsproject.comments.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

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
    @JsonIgnore
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

    @JsonIgnore
    public Comment getComment() {
        return comment;
    }

    public Integer getCommentId() {
        if (comment != null) {
            return comment.getId();
        }
        return null;
    }

    @JsonIgnore
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
