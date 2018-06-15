package com.netcracker.adlitsov.newsproject.comments.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "addDate", allowGetters = true)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy("addDate")
    private List<Comment> children;

    @JsonIgnoreProperties({"parent", "articleId", "children", "authorId", "content", "addDate"})
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne()
    private Comment parent;

    private Integer articleId;

    @NotNull
    private Integer authorId;

    @NotBlank
    private String content;

    private int likesCount = 0;

    private int dislikesCount = 0;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date addDate;

    private boolean hidden = false;

    @JsonBackReference
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public Comment getParent() {
        return parent;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
        switch (vote.type) {
            case LIKE:
                likesCount++;
                break;
            case DISLIKE:
                dislikesCount++;
                break;
        }
    }

    public void deleteVote(Vote vote) {
        this.votes.remove(vote);
        switch (vote.type) {
            case LIKE:
                likesCount--;
                break;
            case DISLIKE:
                dislikesCount--;
                break;
        }
    }

    public Vote getVoteFromUser(int userId) {
        for (Vote v : votes) {
            if (v.getUserId() == userId) {
                return v;
            }
        }
        return null;
    }

    public int getRating() {
        return likesCount - dislikesCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }
}