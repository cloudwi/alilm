package com.project.musinsastocknotificationbot.common.config.security.oauth2;

import com.project.musinsastocknotificationbot.domain.member.entity.vo.Role;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration()
        .getRegistrationId();

    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(Provider.of(registrationId), oAuth2User.getAttributes());

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
        oAuth2Attribute.convertToMap(), userNameAttributeName);
  }
}
