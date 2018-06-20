package com.netcracker.adlitsov.newsproject.mailservice.service;

import com.netcracker.adlitsov.newsproject.mailservice.exception.ForbiddenException;
import com.netcracker.adlitsov.newsproject.mailservice.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.mailservice.exception.VerificationTokenExpiredException;
import com.netcracker.adlitsov.newsproject.mailservice.model.*;
import com.netcracker.adlitsov.newsproject.mailservice.repository.SubscriptionRepository;
import com.netcracker.adlitsov.newsproject.mailservice.repository.UserRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class MailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    JavaMailSender mailSender;

    @Transactional
    public Boolean subscribeUserOnCategory(Authentication auth, SubInfo subInfo) throws MessagingException {
        User user = parseAuth(auth);
        user.setEmail(subInfo.getEmail());
        List<Subscription> subs = new ArrayList<>();
        for (int categoryId : subInfo.getCategoriesId()) {
            Subscription subscription = new Subscription();
            subscription.setCategoryId(categoryId);
            subscription.setUser(user);
            subs.add(subscription);
        }

        User foundUser = userRepository.findUserByIdAndEmailAndSubActiveIsTrue(user.getId(), user.getEmail());
        if (foundUser != null) {
            foundUser.setSubscriptions(subs);
            user = userRepository.save(foundUser);
        } else {
            user.setSubscriptions(subs);
            user = userRepository.save(sendConfirmation(user));
        }
        return user.isSubActive();
    }

    public void sendArticlesInfo(Map<Integer, ArticleMailInfo> articles) throws MessagingException {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.isSubActive()) {
                continue;
            }
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            String message = makeUserMessage(articles, user);
            if (message != null) {
                helper.setText(message, true);
                helper.setTo(user.getEmail());
                helper.setSubject("Новостная рассылка от портала JustNews");
                helper.setFrom("mail@justnews.com");
                mailSender.send(mimeMessage);
            }
        }
    }

    private String makeUserMessage(Map<Integer, ArticleMailInfo> articles, User user) {
        StringBuilder messageBuilder = new StringBuilder("Здравствуйте, " + user.getUsername() + "!<br>");
        messageBuilder.append("Мы сделали для вас подборку самых последних новостей на основании ваших интересов:");

        List<Subscription> subscriptions = user.getSubscriptions();
        boolean isEmpty = true;
        for (Subscription sub : subscriptions) {
            int categoryId = sub.getCategoryId();
            ArticleMailInfo article = articles.get(categoryId);
            if (article != null) {
                messageBuilder.append("<br><center><h3>").append(article.getCategory().getName()).append("</h4></center>");
                messageBuilder.append("<h4>").append(article.getTitle()).append("</h4>");
                messageBuilder.append("<p>").append("<img src=\"" + article.getLogoSrc() + "\" style=\"float:left;width:85px;height:85px;margin-right: 5px; \">")
                              .append(article.getOverview()).append("... ");
                messageBuilder.append("<a href='http://localhost:4200/article/").append(article.getId()).append("'>Читать далее</a>")
                              .append("</p>");
                isEmpty = false;
            }
        }

        if (isEmpty) {
            return null;
        }

        messageBuilder.append("<br><br>Вы всегда можете отписаться от рассылки в ")
                      .append("<a href='http://localhost:4200/user/settings'>настройках</a> вашего аккаунта");
        messageBuilder.append("<br>С уважением, редакция сайта.");

        return messageBuilder.toString();
    }

    public SubInfo getUserSubs(Authentication auth) {
        int userId = parseAuth(auth).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        List<Integer> subs = new ArrayList<>();
        for (Subscription subscription : user.getSubscriptions()) {
            subs.add(subscription.getCategoryId());
        }
        SubInfo subInfo = new SubInfo();
        subInfo.setEmail(user.getEmail());
        subInfo.setCategoriesId(subs);
        subInfo.setActive(user.isSubActive());
        return subInfo;
    }

    public void unsubscribeUser(Authentication auth) {
        userRepository.deleteById(parseAuth(auth).getId());
    }

    @Transactional
    public User confirmUserByToken(String token) {
        User user = userRepository.findUserByVerificationToken_Token(token)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "verificationToken", token));

        VerificationToken verificationToken = user.getVerificationToken();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            // orphanRemoval - this removes verToken from table in db
            user.setVerificationToken(null);
            userRepository.save(user);

            throw new VerificationTokenExpiredException("VerificationToken", "value", token);
        }

        // have no influence on user group (it's separate marker)
        user.setSubActive(true);

        // orphanRemoval - this removes verToken from table in db
        user.setVerificationToken(null);
        return userRepository.save(user);
    }

    public void sendConfirmation(int userId) throws MessagingException {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.isSubActive()) {
            throw new ForbiddenException();
        }

        userRepository.save(sendConfirmation(user));
    }

    private User sendConfirmation(User user) throws MessagingException {
        VerificationToken verificationToken = new VerificationToken();
        user.setVerificationToken(verificationToken);
        verificationToken.setUser(user);

        sendConfirmationMessage(user, verificationToken);

        return user;
    }

    public void sendConfirmationMessage(User user, VerificationToken verificationToken) throws MessagingException {
        String confirmationUrl = "http://localhost:8084/mailing/users/confirm?token=" + verificationToken.getToken();
        String message = "Пожалуйста, подтвердите почту для подписки, перейдя по <a href=\"" + confirmationUrl + "\">ссылке</a>.";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setText(message, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Подтверждение подписки");
        helper.setFrom("mail@justnews.com");
        mailSender.send(mimeMessage);
    }

    public void sendAuthConfirmationMessage(VerificationData data) throws MessagingException {
        String confirmationUrl = "http://localhost:8084/users/confirm?token=" + data.getVerificationToken();
        String message = "Уважаемый " + data.getUserName() + ", пожалуйста, подтвердите почту для завершения регистрации на сайте." +
                "Для этого перейдите по следующей <a href=\"" + confirmationUrl + "\">ссылке</a>.";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setText(message, true);
        helper.setTo(data.getEmail());
        helper.setSubject("Подтверждение регистрации на сайте");
        helper.setFrom("mail@justnews.com");
        mailSender.send(mimeMessage);
    }

    public void sendPasswordChangedMail(PasswordChangedInfo passInfo) throws MessagingException {
        String message = "Уважаемый " + passInfo.getUserName() + ", пароль для вашего аккаунта изменился." +
                "Новый пароль: " + passInfo.getNewPassword();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setText(message, true);
        helper.setTo(passInfo.getEmail());
        helper.setSubject("Настройки аккаунта изменились");
        helper.setFrom("mail@justnews.com");
        mailSender.send(mimeMessage);
    }

    private User parseAuth(Authentication auth) {
        Map<String, Object> details = (Map<String, Object>) ((OAuth2AuthenticationDetails) auth.getDetails()).getDecodedDetails();
        int userId = (int) details.get("user_id");
        String userName = (String) details.get("user_name");

        User user = new User();
        user.setId(userId);
        user.setUsername(userName);
        return user;
    }


}
