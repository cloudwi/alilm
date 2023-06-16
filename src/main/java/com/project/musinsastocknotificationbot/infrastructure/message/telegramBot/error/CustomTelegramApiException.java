package com.project.musinsastocknotificationbot.infrastructure.message.telegramBot.error;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CustomTelegramApiException extends RuntimeException {

  public CustomTelegramApiException(TelegramApiException e) {
  }
}
