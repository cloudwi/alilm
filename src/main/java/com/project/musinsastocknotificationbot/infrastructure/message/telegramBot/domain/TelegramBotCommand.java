package com.project.musinsastocknotificationbot.infrastructure.message.telegramBot.domain;

import java.util.Arrays;

public enum TelegramBotCommand {

  ADD("/add"),
  DELETE("/delete"),
  FIND_ALL("/findAll"),
  DEFAULT("/default");


  private String command;

  TelegramBotCommand(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public static TelegramBotCommand valueOfLInput(String inputMessage) {
    return Arrays.stream(values())
        .filter(value -> value.command.equals(inputMessage))
        .findAny()
        .orElse(DEFAULT);
  }
}