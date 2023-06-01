package com.project.musinsastocknotificationbot.telegramBot.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class TelegramWebClient {
    private final WebClient webClient;
    DefaultUriBuilderFactory defaultUriBuilderFactory;

    private static final String BASE_URL = "https://api.telegram.org/bot";
    private static final String SEND_MESSAGE_SUBURL = "/sendmessage";

    public TelegramWebClient(@Value("${secret.telegramToken}") String token) {
        String telegramBotUrl = BASE_URL + token + SEND_MESSAGE_SUBURL;

        defaultUriBuilderFactory = new DefaultUriBuilderFactory(telegramBotUrl);
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        this.webClient = WebClient.builder()
                .uriBuilderFactory(defaultUriBuilderFactory)
                .baseUrl(telegramBotUrl)
                .build();
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
