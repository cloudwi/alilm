package com.project.musinsastocknotificationbot.telegramBot.error;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CustomTelegramApiException extends RuntimeException {

  public CustomTelegramApiException(TelegramApiException e) {
  }
}
