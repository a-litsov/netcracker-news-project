package com.netcracker.adlitsov.newsproject.authserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private String firstName;

    private String lastName;

    private int rating;

    @NotNull
    private String avatarUrl;

    @NotNull
    private String about;

    @NotNull
    private Date lastOnline;

    @NotNull
    private Date regDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rating=" + rating +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", about='" + about + '\'' +
                ", lastOnline=" + lastOnline +
                ", regDate=" + regDate +
                ", rank=" + rank +
                '}';
    }
}
