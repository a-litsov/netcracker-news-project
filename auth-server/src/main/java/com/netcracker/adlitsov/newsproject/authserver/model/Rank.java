package com.netcracker.adlitsov.newsproject.authserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "rank")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String color;

    @NotNull
    private int ratingThreshold;

    @JsonIgnore
    @OneToMany(mappedBy = "rank", cascade = CascadeType.ALL)
    private List<UserInfo> usersDetails;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRatingThreshold() {
        return ratingThreshold;
    }

    public void setRatingThreshold(int ratingThreshold) {
        this.ratingThreshold = ratingThreshold;
    }

    public List<UserInfo> getUsersDetails() {
        return usersDetails;
    }

    public void setUsersDetails(List<UserInfo> usersDetails) {
        this.usersDetails = usersDetails;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", ratingThreshold=" + ratingThreshold +
                '}';
    }
}