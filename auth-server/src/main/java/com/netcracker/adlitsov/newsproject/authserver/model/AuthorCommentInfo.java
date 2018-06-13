package com.netcracker.adlitsov.newsproject.authserver.model;

public class AuthorCommentInfo {
    private int id;
    private String username;
    private String avatarUrl;

    public AuthorCommentInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatarUrl = user.getProfile().getAvatarUrl();
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
}
