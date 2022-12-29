package com.project.musinsastocknotificationbot.domain.telegram.service;

import com.project.musinsastocknotificationbot.domain.product.service.ProductService;
import com.project.musinsastocknotificationbot.domain.telegram.entity.TelegramWebClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramService {

    private final ProductService productService;
    private final WebClient webClient;
    Document doc = null;

    public TelegramService(ProductService productService, TelegramWebClient telegramWebClient) {
        this.productService = productService;
        this.webClient = telegramWebClient.getWebClient();
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void sendMessageScheduled() {
        StringBuilder stringBuilder = new StringBuilder();

        productService.findAll().forEach(product -> {
            String url = "https://www.musinsa.com/app/goods/" + product.getProductId().getId();

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements element = doc.select("div.option_cont.select");
        });

        String encodeResult = URLEncoder.encode("띄어쓰 기 가 안 됨", StandardCharsets.UTF_8);
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("chat_id", "5812221822")
                        .queryParam("text", encodeResult)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
