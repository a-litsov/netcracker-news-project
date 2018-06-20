package com.netcracker.adlitsov.newsproject.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@Order(1)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ApplicationContext applicationContext;

    // Provided in AuthConfig
    @Autowired
    private TokenStore tokenStore;

    // To allow the ResourceServerConfigurerAdapter to understand the token,
    // it must share the same characteristics with AuthorizationServerConfigurerAdapter.
    // So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .tokenStore(tokenStore)
                .expressionHandler(getOAuth2ExpressionHandler(applicationContext));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // UserController
                .antMatchers(HttpMethod.PUT, "/users/recovery-password").permitAll()
                .antMatchers("/users/authors-comment-info").permitAll()
                .antMatchers("/users/register", "/users/confirm", "/users/{\\d+}/send-confirmation").permitAll()
                .antMatchers("/users/create").hasAuthority("OP_CREATE_USER")
                .antMatchers("/users/{\\d+}/mute", "/users/{\\d+}/unmute").hasAuthority("OP_MUTE_USER")
                .antMatchers("/users/{\\d+}/ban", "/users/{\\d+}/unban").hasAuthority("OP_BAN_USER")
                // SettingsController
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().authenticated();
    }

    // setting default expression handler (need to
    private OAuth2WebSecurityExpressionHandler getOAuth2ExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}
