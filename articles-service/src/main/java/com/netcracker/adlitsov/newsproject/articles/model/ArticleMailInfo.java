package com.netcracker.adlitsov.newsproject.articles.model;

import java.util.Date;

public class ArticleMailInfo {
    private static final int OVERVIEW_LENGTH = 300;
    private int id;
    private String title;
    private Category category;
    private String logoSrc;
    private Date addDate;
    private String overview;

    public ArticleMailInfo() {

    }

    public ArticleMailInfo(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.category = article.getCategory();
        this.logoSrc = article.getLogoSrc();
        this.addDate = article.getAddDate();
        this.overview = article.getContent().substring(0, OVERVIEW_LENGTH);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLogoSrc() {
        return logoSrc;
    }

    public void setLogoSrc(String logoSrc) {
        this.logoSrc = logoSrc;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
