package com.project.musinsastocknotificationbot.common.config.security.oauth2;

import java.util.Arrays;

public enum Provider {

  NAVER("naver"), KAKAO("kakao"), DEFAULT("default");

  private final String key;

  Provider(String key) {
    this.key = key;
  }

  public String getKey() {
    return this.key;
  }

  public static Provider of(String key) {
    return Arrays.stream(Provider.values())
        .filter(provider -> provider.key.equals(key))
        .findFirst()
        .orElse(Provider.DEFAULT);
  }
}
