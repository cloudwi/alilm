package com.project.musinsastocknotificationbot.telegramBot.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class TelegramWebClient {
    private final WebClient webClient;
    DefaultUriBuilderFactory defaultUriBuilderFactory;

    public TelegramWebClient(@Value("${secret.telegramToken}") String token) {
        String sendMessageSubUrl = "/sendmessage";
        String baseUrl = "https://api.telegram.org/bot";
        String telegramUrl = baseUrl + token + sendMessageSubUrl;

        defaultUriBuilderFactory = new DefaultUriBuilderFactory(telegramUrl);
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        this.webClient = WebClient.builder()
                .uriBuilderFactory(defaultUriBuilderFactory)
                .baseUrl(telegramUrl)
                .build();
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
