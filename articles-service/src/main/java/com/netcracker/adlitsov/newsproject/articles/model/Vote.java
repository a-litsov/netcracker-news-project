package com.netcracker.adlitsov.newsproject.articles.model;

// TODO: user everywhere com.fasterxml.*.. packages where json figures
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"article_id", "user_id"})
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
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @JsonBackReference
    private Article article;

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
    public Article getArticle() {
        return article;
    }

    public Integer getArticleId() {
        if (article != null) {
            return article.getId();
        }
        return null;
    }

    @JsonBackReference
    public void setArticle(Article article) {
        this.article = article;
    }

    public VoteType getType() {
        return type;
    }

    public void setType(VoteType type) {
        this.type = type;
    }
}
