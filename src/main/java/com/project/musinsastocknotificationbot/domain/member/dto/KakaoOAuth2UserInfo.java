package com.project.musinsastocknotificationbot.domain.member.dto;

import java.util.Map;

public class KakaoOAuth2UserInfo {

  private Map<String, Object> attributes;

  public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public String getEmail() {
    return (String) attributes.get("email");
  }
}
