package com.netcracker.adlitsov.newsproject.authserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.netcracker.adlitsov.newsproject.authserver.converter.GenderConverter;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final GenderConverter GENDER_CONVERTER = new GenderConverter();

    public static enum Gender {
        MALE, FEMALE
    }

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private String firstName;

    private String lastName;

    private int rating;

    @NotNull
    private String avatarUrl = "https://www.worldskills.org/components/angular-worldskills-utils/images/user.png";

    @NotNull
    private String about = "Этот пользователь предпочёл пока не указывать информации о себе";

    @NotNull
    private Date lastOnline = new Date();

    @NotNull
    private Date regDate = new Date();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;

    private String country;

    private String city;

    private Date birthDate;

    @Convert(converter = GenderConverter.class)
    private Gender gender;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return user.getUsername();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @JsonIgnore
    public Gender getGender() {
        return gender;
    }

    @JsonIgnore
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @JsonGetter(value = "gender")
    public String getGenderText() {
        return GENDER_CONVERTER.convertToDatabaseColumn(gender);
    }

    @JsonSetter(value = "gender")
    public void setGenderText(String gender) {
        this.gender = GENDER_CONVERTER.convertToEntityAttribute(gender);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rating=" + rating +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", about='" + about + '\'' +
                ", lastOnline=" + lastOnline +
                ", regDate=" + regDate +
                ", rank=" + rank +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
    }
}
