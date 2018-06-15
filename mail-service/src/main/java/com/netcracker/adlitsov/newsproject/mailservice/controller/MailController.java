package com.netcracker.adlitsov.newsproject.mailservice.controller;

import com.netcracker.adlitsov.newsproject.mailservice.model.ArticleMailInfo;
import com.netcracker.adlitsov.newsproject.mailservice.model.SubInfo;
import com.netcracker.adlitsov.newsproject.mailservice.model.User;
import com.netcracker.adlitsov.newsproject.mailservice.service.MailService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class MailController {

    @Autowired
    ArticlesServiceProxy articlesServiceProxy;

    @Autowired
    MailService mailService;

    // every 10 secs by default
    @Scheduled(cron = "${cron:#{'0/10 * * * * *'}}")
    public void makeEverydayMailing() throws MessagingException {
        System.out.println("Mailing begins..");
        Map<Integer, ArticleMailInfo> articles = articlesServiceProxy.getAllMailArticles();
        mailService.sendArticlesInfo(articles);

    }

    // orphanRemoval - only listed subscriptions will be saved (others will be removed)
    @PostMapping("/mailing/subscribe")
    public void subscribeUserOnCategory(Authentication authentication, @RequestBody SubInfo subInfo) {
        mailService.subscribeUserOnCategory(authentication, subInfo);
    }

    @PostMapping("/mailing/get-subs")
    public List<Integer> subscribeUserOnCategory(Authentication authentication, @RequestBody String email) {
        return mailService.getUserSubs(authentication, email);
    }
}
