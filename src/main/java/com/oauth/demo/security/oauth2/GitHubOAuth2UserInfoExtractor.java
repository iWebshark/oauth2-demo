package com.oauth.demo.security.oauth2;

import com.oauth.demo.model.User;
import com.oauth.demo.persistence.model.Role;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.oauth.demo.persistence.model.OAuth2Provider.GITHUB;

@Service
public class GitHubOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

    @Override
    public User extractUserInfo(OAuth2User oAuth2User) {
        return User.builder()
                .email(retrieveAttr("email", oAuth2User))
                .firstname(retrieveAttr("name", oAuth2User))
                .username(retrieveAttr("login", oAuth2User))
                .provider(GITHUB)
                .role(Role.USER)
                .build();
    }

    @Override
    public boolean accepts(OAuth2UserRequest userRequest) {
        return GITHUB.name().equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId());
    }

    private String retrieveAttr(String attr, OAuth2User oAuth2User) {
        Object attribute = oAuth2User.getAttributes().get(attr);
        return attribute == null ? "" : attribute.toString();
    }
}
