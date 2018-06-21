package com.netcracker.adlitsov.newsproject.mailservice.controller;

import com.netcracker.adlitsov.newsproject.mailservice.model.*;
import com.netcracker.adlitsov.newsproject.mailservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
public class MailController {

    @Autowired
    ArticlesServiceProxy articlesServiceProxy;

    @Autowired
    MailService mailService;

    // every 10 secs by default
    @Scheduled(cron = "${cron:#{'0 0/1 * * * *'}}")
    public void makeEverydayMailing() throws MessagingException {
        System.out.println("Mailing begins..");
        Map<Integer, ArticleMailInfo> articles = articlesServiceProxy.getAllMailArticles();
        mailService.sendArticlesInfo(articles);

    }

    // orphanRemoval - only listed subscriptions will be saved (others will be removed)
    @PostMapping("/mailing/subscribe")
    public Boolean subscribeUserOnCategory(Authentication authentication, @RequestBody SubInfo subInfo) throws MessagingException {
        return mailService.subscribeUserOnCategory(authentication, subInfo);
    }

    @GetMapping("/mailing/users/{id}/send-confirmation")
    public void sendConfirmation(@PathVariable("id") Integer id) throws MessagingException {
        mailService.sendConfirmation(id);
    }

    @GetMapping(value = "/mailing/users/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        User confirmedUser = mailService.confirmUserByToken(token);
        if (confirmedUser != null) {
            return new ResponseEntity<>("Подписка на почту " + confirmedUser.getEmail() + " успешно подтверждена, "
                                                + confirmedUser.getUsername() + "!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Не удалось подтвердить почту! Попробуйте ещё раз.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mailing/password-changed")
    public void sendPasswordChangedMail(@RequestBody PasswordChangedInfo passwordChangedInfo) throws MessagingException {
        mailService.sendPasswordChangedMail(passwordChangedInfo);
    }

    @GetMapping("/mailing/get-subinfo")
    public SubInfo getSubInfo(Authentication authentication) {
        return mailService.getUserSubs(authentication);
    }

    @PostMapping("/mailing/unsubscribe")
    public void unsubscribeUser(Authentication authentication) {
        mailService.unsubscribeUser(authentication);
    }

    @PostMapping("/mailing/send-confirmation")
    void sendAuthConfirmationMessage(@RequestBody VerificationData data) throws MessagingException {
        mailService.sendAuthConfirmationMessage(data);
    }

}
