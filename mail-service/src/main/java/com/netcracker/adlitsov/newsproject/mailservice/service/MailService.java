package com.netcracker.adlitsov.newsproject.mailservice.service;

import com.netcracker.adlitsov.newsproject.mailservice.model.ArticleMailInfo;
import com.netcracker.adlitsov.newsproject.mailservice.model.SubInfo;
import com.netcracker.adlitsov.newsproject.mailservice.model.Subscription;
import com.netcracker.adlitsov.newsproject.mailservice.model.User;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    JavaMailSender mailSender;

    @Transactional
    public void subscribeUserOnCategory(Authentication auth, SubInfo subInfo) {
        User user = parseAuth(auth);
        user.setEmail(subInfo.getEmail());
        for (int categoryId: subInfo.getCategoriesId()) {
            Subscription subscription = new Subscription();
            subscription.setCategoryId(categoryId);
            subscription.setUser(user);
            user.addSubscription(subscription);
        }

        User savedUser = userRepository.save(user);
    }

    public void sendArticlesInfo(Map<Integer, ArticleMailInfo> articles) throws MessagingException {
        List<User> users = userRepository.findAll();
        for (User user : users) {
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
                messageBuilder.append("<p>").append(article.getOverview()).append("... ");
                messageBuilder.append("<a href='http://localhost:4200/article/").append(article.getId()).append("'>Читать далее</a>");
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

    private User parseAuth(Authentication auth) {
        Map<String, Object> details = (Map<String, Object>)((OAuth2AuthenticationDetails)auth.getDetails()).getDecodedDetails();
        int userId = (int)details.get("user_id");
        String userName = (String)details.get("user_name");

        User user = new User();
        user.setId(userId);
        user.setUsername(userName);
        return user;
    }


}
