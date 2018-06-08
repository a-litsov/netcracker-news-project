package com.netcracker.adlitsov.newsproject.authserver.config;

import com.netcracker.adlitsov.newsproject.authserver.model.User;
import com.netcracker.adlitsov.newsproject.authserver.service.MyUserPrincipal;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class MyJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // adding user_id field to token if auth type == password (i.e. we have concrete user not just client)
        if(authentication.getOAuth2Request().getGrantType().equalsIgnoreCase("password")) {
            MyUserPrincipal user = (MyUserPrincipal) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>();

            additionalInfo.put("user_id", user.getId());

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }

        // converting our default type oauth access token to jwt token
        return super.enhance(accessToken, authentication);
    }
}
