package com.netcracker.adlitsov.newsproject.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/articles/**").hasAuthority("OP_ADD_ARTICLE")
                .antMatchers(HttpMethod.PUT, "/articles/**").hasAuthority("OP_UPDATE_ARTICLE")
                .antMatchers(HttpMethod.DELETE, "/articles/**").hasAuthority("OP_DELETE_ARTICLE")
                .antMatchers(HttpMethod.POST, "/categories/**").hasAuthority("OP_ADD_CATEGORY")
                .antMatchers(HttpMethod.PUT, "/categories/**").hasAuthority("OP_UPDATE_CATEGORY")
                .antMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("OP_DELETE_CATEGORY")
                .antMatchers(HttpMethod.POST, "/tags/**").hasAuthority("OP_ADD_TAG")
                .antMatchers(HttpMethod.PUT, "/tags/**").hasAuthority("OP_UPDATE_TAG")
                .antMatchers(HttpMethod.DELETE, "/tags/**").hasAuthority("OP_DELETE_TAG")

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