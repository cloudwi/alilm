package com.project.musinsastocknotificationbot.domain.telegram.service;

import com.project.musinsastocknotificationbot.domain.product.service.ProductService;
import com.project.musinsastocknotificationbot.domain.telegram.entity.TelegramWebClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
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
    private final String chatId;

    public TelegramService(ProductService productService, TelegramWebClient telegramWebClient, @Value("${secret.chat_id}") String chatId) {
        this.productService = productService;
        this.webClient = telegramWebClient.getWebClient();
        this.chatId = chatId;
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void sendMessageScheduled() {
        StringBuilder stringBuilder = new StringBuilder();

        productService.findAll().forEach(product -> {
            String url = "https://www.musinsa.com/app/goods/" + product.getProductId().getId();

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements elements = doc.select("select.option1");
            String title = doc.select("span.product_title").text();
            for (Element element : elements.select("option")) {
                String target = element.text();
                if (target.contains(product.getProductId().getSize()) && !target.contains("품절")) {
                    stringBuilder.append(title);
                    stringBuilder.append(" : ");
                    stringBuilder.append("구매가능");
                    stringBuilder.append("\n");
                    productService.delete(product.getProductId());
                }
            }
        });

        if (stringBuilder.isEmpty()) {

        } else {
            String encodeResult = URLEncoder.encode(stringBuilder.toString(), StandardCharsets.UTF_8);
            webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("chat_id", chatId)
                            .queryParam("text", encodeResult)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }
}
