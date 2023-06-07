package com.project.musinsastocknotificationbot.common.config.security.oauth2.dto;

import com.project.musinsastocknotificationbot.domain.member.entity.Member;
import com.project.musinsastocknotificationbot.domain.member.entity.vo.Role;
import java.util.HashMap;
import java.util.Map;

public record OAuth2Attribute(
    Map<String, Object> attributes,
    String provider,
    String attributeKey,
    String email
) {

  public static OAuth2Attribute of(String provider, String attributeKey,
      Map<String, Object> attributes) {
    switch (provider) {
      case "kakao" -> {
        return ofKakao(provider, attributeKey, attributes);
      }
      default -> {
        throw new RuntimeException("정상적인 url이 아닙니다.");
      }
    }
  }

  private static OAuth2Attribute ofKakao(String provider, String attributeKey,
      Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

    return new OAuth2Attribute(attributes, provider, attributeKey,
        (String) kakaoAccount.get("email")
    );
  }

  public Map<String, Object> convertToMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", this.attributes);
    map.put("provider", this.provider);
    map.put("attributeKey", this.attributeKey);
    map.put("email", this.email);

    return map;
  }

  public Member toEntity() {
    return new Member(this.email, this.provider, Role.USER);
  }
}
