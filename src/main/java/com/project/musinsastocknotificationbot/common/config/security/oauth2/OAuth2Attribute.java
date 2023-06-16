package com.project.musinsastocknotificationbot.common.config.security.oauth2;

import java.util.HashMap;
import java.util.Map;

public record OAuth2Attribute(
    Map<String, Object> attributes,
    Provider provider,
    String email
) {

  public static OAuth2Attribute of(Provider provider ,
      Map<String, Object> attributes) {
    switch (provider) {
      case NAVER -> {
        return ofNaver(provider, attributes);
      }
      case KAKAO -> {
        return ofKakao(provider, attributes);
      }
      default -> throw new RuntimeException("정상적인 url이 아닙니다.");

    }
  }

  private static OAuth2Attribute ofNaver(Provider provider,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return new OAuth2Attribute(attributes, provider, (String) response.get("email"));
  }

  private static OAuth2Attribute ofKakao(Provider provider,
      Map<String, Object> attributes) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

    return new OAuth2Attribute(attributes, provider, (String) kakaoAccount.get("email"));
  }

  public Map<String, Object> convertToMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("response", this.attributes);
    map.put("provider", this.provider);
    map.put("email", this.email);

    return map;
  }
}
