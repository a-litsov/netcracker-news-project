package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationToken;

public interface MailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendConfirmationMessage(User user, VerificationToken verificationToken);
}
