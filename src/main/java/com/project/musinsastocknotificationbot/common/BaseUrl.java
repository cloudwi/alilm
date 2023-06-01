package com.project.musinsastocknotificationbot.common;

public enum BaseUrl {
  MUSINSA_PRODUCT_BASE_URL("https://www.musinsa.com/app/goods/");

  private final String url;

  BaseUrl(String url) {
    this.url = url;
  }

  public String getBaseUrl() {
    return this.url;
  }
}
