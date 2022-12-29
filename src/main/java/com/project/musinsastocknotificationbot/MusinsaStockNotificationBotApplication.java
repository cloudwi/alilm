package com.project.musinsastocknotificationbot;

import com.project.musinsastocknotificationbot.domain.telegramBot.entity.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class MusinsaStockNotificationBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaStockNotificationBotApplication.class, args);
    }

}
