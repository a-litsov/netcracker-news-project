package com.netcracker.adlitsov.newsproject.authserver.config;

import com.netcracker.adlitsov.newsproject.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthConfig extends AuthorizationServerConfigurerAdapter {
    private static final int TWO_HOURS = 2 * 3600;

    @Autowired
    UserService userService;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("website")
                .scopes("ARTICLE", "CATEGORY", "TAG", "COMMENT", "REGISTER_USER")
                .autoApprove(true)
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .secret(passwordEncoder.encode("website-secret"))
                .accessTokenValiditySeconds(TWO_HOURS)
                .and()
            .withClient("auth-service")
                .autoApprove(true)
                .scopes("mail")
                .authorizedGrantTypes("client_credentials", "password")
                .secret(passwordEncoder.encode("auth-service-secret"))
                .accessTokenValiditySeconds(TWO_HOURS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager);
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        // setting public/private keys pair for RSASHA256 signing algorithm
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "mySecretKey".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverterWithId();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));

        // setting our UserDetailsService in AuthConverter (it's used to get principal from our auth as
        // UserPrincipal object): Authentication -> UserPrincipal; without this we will get only
        // string in call like auth.getPrincipal()
        DefaultUserAuthenticationConverter duac = new DefaultUserAuthenticationConverter();
        duac.setUserDetailsService(userService);
        DefaultAccessTokenConverter datc = new DefaultAccessTokenConverter();
        datc.setUserTokenConverter(duac);

        converter.setAccessTokenConverter(datc);
        return converter;
    }
}