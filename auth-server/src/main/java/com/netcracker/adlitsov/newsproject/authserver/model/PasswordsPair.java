package com.netcracker.adlitsov.newsproject.authserver.model;

public class PasswordsPair {
    private String oldPassword;
    private String newPassword;

    public PasswordsPair() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
