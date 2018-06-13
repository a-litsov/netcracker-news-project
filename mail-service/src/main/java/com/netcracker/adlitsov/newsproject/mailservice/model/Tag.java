package com.netcracker.adlitsov.newsproject.mailservice.model;

import java.io.Serializable;

public class Tag implements Serializable {
    private Integer id;

    private String name;

    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}