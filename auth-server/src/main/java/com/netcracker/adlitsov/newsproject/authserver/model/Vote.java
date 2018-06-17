package com.netcracker.adlitsov.newsproject.authserver.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"receiver_id", "author_id"})
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
    @JsonBackReference
    private Profile receiver;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    @JsonBackReference
    private Profile author;

    @Min(1)
    @Max(5)
    private int value;

    public Integer getId() {
        return id;
    }

    public Profile getReceiver() {
        return receiver;
    }

    public void setReceiver(Profile receiver) {
        this.receiver = receiver;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Integer getReceiverId() {
        return receiver.getUser().getId();
    }

    public Integer getAuthorId() {
        return author.getUser().getId();
    }
}
