package com.hanghae.justpotluck.global.security.oauth;

import com.hanghae.justpotluck.global.exception.OAuth2AuthenticationProcessingException;
import com.hanghae.justpotluck.domain.user.entity.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        System.err.println(attributes);
        switch (authProvider) {
            case kakao:
                return new KakaoOAuth2UserInfo(attributes);
            case google:
                return new GoogleOAuth2UserInfo(attributes);
            default:
                throw new OAuth2AuthenticationProcessingException("Invalid Provider");
        }
    }
}