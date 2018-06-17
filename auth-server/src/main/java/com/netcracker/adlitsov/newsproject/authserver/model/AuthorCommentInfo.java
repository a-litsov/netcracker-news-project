package com.netcracker.adlitsov.newsproject.authserver.model;

public class AuthorCommentInfo {
    private int id;
    private String username;
    private String avatarUrl;
    private double rating;

    public AuthorCommentInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatarUrl = user.getProfile().getAvatarUrl();
        this.rating = user.getProfile().getRating();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
