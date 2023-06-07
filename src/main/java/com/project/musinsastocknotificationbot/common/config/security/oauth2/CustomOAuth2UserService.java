package com.project.musinsastocknotificationbot.common.config.security.oauth2;

import com.project.musinsastocknotificationbot.common.config.security.oauth2.dto.OAuth2Attribute;
import com.project.musinsastocknotificationbot.domain.member.entity.Member;
import com.project.musinsastocknotificationbot.domain.member.repository.MemberRepository;
import java.util.Collections;
import org.h2.engine.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberRepository memberRepository;

  public CustomOAuth2UserService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration()
        .getRegistrationId(); // kakao
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    Member member = saveOrUpdate(oAuth2Attribute);

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
        oAuth2Attribute.convertToMap(), userNameAttributeName);
  }

  private Member saveOrUpdate(OAuth2Attribute oAuth2Attribute) {
    Member member = memberRepository.findByEmail(oAuth2Attribute.email())
        .map(entity -> entity.update(oAuth2Attribute.provider()))
        .orElse(oAuth2Attribute.toEntity());

    return memberRepository.save(member);
  }
}
