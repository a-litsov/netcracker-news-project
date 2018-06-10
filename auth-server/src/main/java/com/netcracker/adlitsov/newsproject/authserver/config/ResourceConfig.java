package com.netcracker.adlitsov.newsproject.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@Order(1)
public class ResourceConfig extends ResourceServerConfigurerAdapter{

    // Provided in AuthConfig
    @Autowired
    private TokenStore tokenStore;

    // To allow the ResourceServerConfigurerAdapter to understand the token,
    // it must share the same characteristics with AuthorizationServerConfigurerAdapter.
    // So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/create-user").access("#oauth2.hasScope('REGISTER_USER') and hasAuthority('OP_CREATE_USER')")
                .antMatchers(HttpMethod.GET, "/profiles/**").permitAll()
                .antMatchers("/profiles").access("!hasAuthority('ROLE_BANNED')")
                .antMatchers("/profiles/*").access("hasAuthority('ROLE_MODERATOR') or hasAuthority('ROLE_ADMIN')");
    }

}
