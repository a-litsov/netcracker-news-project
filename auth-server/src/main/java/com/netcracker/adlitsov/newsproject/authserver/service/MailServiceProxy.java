package com.netcracker.adlitsov.newsproject.authserver.service;

import com.netcracker.adlitsov.newsproject.authserver.config.MailServiceClientConfiguration;
import com.netcracker.adlitsov.newsproject.authserver.model.PasswordChangedInfo;
import com.netcracker.adlitsov.newsproject.authserver.model.VerificationData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;

// TODO: gateway
@FeignClient(name = "mail-service", url="http://localhost:8084", configuration = MailServiceClientConfiguration.class)
public interface MailServiceProxy {
    @PostMapping("/mailing/send-confirmation")
    void sendAuthConfirmationMessage(@RequestBody VerificationData data);

    @PostMapping("/mailing/password-changed")
    public void sendPasswordChangedMail(@RequestBody PasswordChangedInfo passwordChangedInfo);
}