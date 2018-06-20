package com.netcracker.adlitsov.newsproject.mailservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name =  "`user`")
public class User {

    @Id
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @OneToMany(mappedBy =  "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Subscription> subscriptions = new ArrayList<>();

    private boolean subActive = false;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private VerificationToken verificationToken;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void addSubscription(Subscription sub) {
        this.subscriptions.add(sub);
    }

    public void removeSubscription(Subscription sub) {
        this.subscriptions.removeIf((s) -> s.getCategoryId() == sub.getCategoryId());
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        if (subscriptions == null) {
            this.subscriptions = subscriptions;
        } else {
            this.subscriptions.retainAll(subscriptions);
            this.subscriptions.addAll(subscriptions);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubActive() {
        return subActive;
    }

    public void setSubActive(boolean subActive) {
        this.subActive = subActive;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }
}
