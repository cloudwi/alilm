package com.project.musinsastocknotificationbot.infrastructure.message.telegramBot.error;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramApiConnectionException extends RuntimeException {

  public TelegramApiConnectionException(TelegramApiException e) {
  }
}
