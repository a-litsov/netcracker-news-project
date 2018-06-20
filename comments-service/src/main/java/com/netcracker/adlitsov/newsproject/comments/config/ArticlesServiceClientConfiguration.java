package com.netcracker.adlitsov.newsproject.comments.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ArticlesServiceClientConfiguration {

   @Bean
   public RequestInterceptor oauth2FeignRequestInterceptor() {
      return new AuthForwardInterceptor();
   }

   public static class AuthForwardInterceptor implements RequestInterceptor {

      @Override
      public void apply(RequestTemplate template) {
         HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         template.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
      }
   }
 
}