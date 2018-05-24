package com.netcracker.adlitsov.newsproject.articles.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "article")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "addDate", allowGetters = true)
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @NotBlank
    private String title;

    @NotBlank
    private String logoSrc;

    @NotBlank
    private String content;

    @NotBlank
    private String authorName;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date addDate;

    public Article() {
    }

    public Integer getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogoSrc() {
        return logoSrc;
    }

    public void setLogoSrc(String logoSrc) {
        this.logoSrc = logoSrc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public ArticlePreview getPreview() {
        ArticlePreview preview = new ArticlePreview();

        preview.setId(getId());
        preview.setCategory(getCategory());
        preview.setTag(getTag());
        preview.setLogoSrc(getLogoSrc());
        preview.setTitle(getTitle());

        return preview;
    }
}
