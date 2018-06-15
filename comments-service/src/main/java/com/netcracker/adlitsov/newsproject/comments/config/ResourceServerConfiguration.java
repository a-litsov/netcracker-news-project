package com.netcracker.adlitsov.newsproject.comments.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/comments/{\\d+}/like", "/comments/{\\d+}/dislike")
                    .hasAuthority("OP_VOTE_COMMENT")

                .antMatchers(HttpMethod.POST, "/comments/**").hasAuthority("OP_ADD_COMMENT")
                .antMatchers(HttpMethod.PUT, "/comments/{\\d+}/hide", "/comments/{\\d+}/show").hasAuthority("OP_HIDE_COMMENT")
                .antMatchers(HttpMethod.PUT, "/comments/{\\d+}").hasAuthority("OP_UPDATE_COMMENT")
                .antMatchers(HttpMethod.DELETE, "/comments/**").hasAuthority("OP_DELETE_COMMENT")

                .anyRequest().permitAll();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore);
    }

    @Autowired
    TokenStore tokenStore;

    @Autowired
    JwtAccessTokenConverter tokenConverter;
}