package com.project.musinsastocknotificationbot.message.infrastructure.telegramBot.webClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

public class TelegramWebClient {

  private final WebClient webClient;
  DefaultUriBuilderFactory defaultUriBuilderFactory;

  public TelegramWebClient(@Value("${secret.telegramToken}") String token) {
    String BASE_URL = "https://api.telegram.org/bot" + token + "/sendmessage";

    defaultUriBuilderFactory = new DefaultUriBuilderFactory(BASE_URL);
    defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
    this.webClient = WebClient.builder()
        .uriBuilderFactory(defaultUriBuilderFactory)
        .baseUrl(BASE_URL)
        .build();
  }

  public WebClient getWebClient() {
    return webClient;
  }


}
