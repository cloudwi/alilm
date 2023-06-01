package com.project.musinsastocknotificationbot.telegramBot.domain;

public enum TelegramBotCommand {

  ADD("/add"),
  DELETE("/delete"),
  FIND_ALL("/findAll");

  private String command;

  TelegramBotCommand(String command) {
    this.command = command;
  }
}
