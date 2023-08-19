package com.project.stocknotificationbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class StockNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockNotificationApplication.class, args);
    }
}