package com.netcracker.adlitsov.newsproject.mailservice.model;

import java.util.List;

public class SubInfo {
    private String email;
    private List<Integer> categoriesId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(List<Integer> categoriesId) {
        this.categoriesId = categoriesId;
    }
}
