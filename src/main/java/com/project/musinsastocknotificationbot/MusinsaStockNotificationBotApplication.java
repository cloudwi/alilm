package com.project.musinsastocknotificationbot;

import com.project.musinsastocknotificationbot.domain.telegram.service.TelegramService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class MusinsaStockNotificationBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MusinsaStockNotificationBotApplication.class, args);
    }

}
