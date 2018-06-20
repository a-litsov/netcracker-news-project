package com.netcracker.adlitsov.newsproject.authserver.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

public class MailServiceClientConfiguration {
 
   @Value("${security.oauth2.client.access-token-uri}")
   private String accessTokenUri;
   @Value("${security.oauth2.client.client-id}")
   private String clientId;
   @Value("${security.oauth2.client.client-secret}")
   private String clientSecret;
   @Value("${security.oauth2.client.scope}")
   private String scope;
 
   @Bean
   public RequestInterceptor oauth2FeignRequestInterceptor() {
      return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
   }
 
   @Bean
   Logger.Level feignLoggerLevel() {
      return Logger.Level.FULL;
   }
 
   private OAuth2ProtectedResourceDetails resource() {
      ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
      resourceDetails.setAccessTokenUri(accessTokenUri);
      resourceDetails.setClientId(clientId);
      resourceDetails.setClientSecret(clientSecret);
      resourceDetails.setGrantType("client_credentials");
      resourceDetails.setScope(Arrays.asList(scope));
      return resourceDetails;
   }
 
}