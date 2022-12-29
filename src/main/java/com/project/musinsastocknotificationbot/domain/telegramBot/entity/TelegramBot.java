package com.project.musinsastocknotificationbot.domain.telegramBot.entity;

import com.project.musinsastocknotificationbot.domain.telegramBot.service.TelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBot {
    private TelegramBotsApi telegramBotsApi;

    public TelegramBot(TelegramService telegramService) {
        try {
            this.telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramService);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
