package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender emailSender;

    @Autowired
    private UserService userService;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendConfirmationMessage(User user, VerificationToken verificationToken) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:9999/user/confirm?token=" + verificationToken.getToken();
        String message = "Registration successful! Please, confirm email: " + confirmationUrl;

        sendSimpleMessage(recipientAddress, subject, message);
    }
}