package com.project.musinsastocknotificationbot.message.infrastructure.telegramBot.domain;

import com.project.musinsastocknotificationbot.message.infrastructure.telegramBot.service.TelegramMessageServiceImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBot {

  private final TelegramBotsApi telegramBotsApi;

  public TelegramBot(TelegramMessageServiceImpl telegramMessageService) {
    try {
      this.telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      this.telegramBotsApi.registerBot(telegramMessageService);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
