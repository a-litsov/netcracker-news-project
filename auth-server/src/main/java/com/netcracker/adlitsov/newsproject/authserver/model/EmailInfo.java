package com.netcracker.adlitsov.newsproject.authserver.model;

public class EmailInfo {

    private String email;
    private boolean isConfirmed;

    public EmailInfo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
