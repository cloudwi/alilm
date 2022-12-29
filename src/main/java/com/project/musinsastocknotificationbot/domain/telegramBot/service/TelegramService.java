package com.project.musinsastocknotificationbot.domain.telegramBot.service;

import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.domain.product.repository.ProductRepository;
import com.project.musinsastocknotificationbot.domain.telegramBot.entity.TelegramWebClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramService extends TelegramLongPollingBot {

    private final String telegramToken;
    private final Logger log = LoggerFactory.getLogger(TelegramService.class);
    private final ProductRepository productRepository;
    private Document doc;
    private WebClient webClient;
    private String chatId;

    public TelegramService(@Value("${secret.telegramToken}") String telegramToken, ProductRepository productRepository, TelegramWebClient telegramWebClient, @Value("${secret.chat_id}") String chatId) {
        this.telegramToken = telegramToken;
        this.productRepository = productRepository;
        this.doc = null;
        this.webClient = telegramWebClient.getWebClient();
        this.chatId = chatId;
    }

    @Override
    public String getBotUsername() {
        return "cloudwi";
    }

    @Override
    public String getBotToken() {
        return telegramToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String[] message = update.getMessage().getText().toString().split(" ");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        switch (message[0]) {
            case "/add" -> {
                String[] productInfo = message[1].split(",");
                ProductId productId = new ProductId(Long.parseLong(productInfo[0]), productInfo[1]);

                String url = "https://www.musinsa.com/app/goods/" + productInfo[0];

                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String title = doc.select("span.product_title").text();
                Product product = new Product(productId,title);
                productRepository.save(product);
            }
            case "/findAll" -> {
                StringBuilder stringBuilder = new StringBuilder();
                productRepository.findAll().forEach(product -> {
                    stringBuilder.append("Id : ");
                    stringBuilder.append(product.getProductId().getId());
                    stringBuilder.append(" Size : ");
                    stringBuilder.append(product.getProductId().getSize());
                    stringBuilder.append(" Title : ");
                    stringBuilder.append(product.getTitle());
                    stringBuilder.append("\n");
                });
                sendMessage.setText(stringBuilder.toString());
            }
            case "/delete" -> {
                String[] productInfo = message[1].split(",");
                ProductId productId = new ProductId(Long.parseLong(productInfo[0]), productInfo[1]);
                productRepository.deleteById(productId);
            }
            default -> {
                sendMessage.setText("""
                        올바른 명령어를 입력해주세요!
                        1. /add {id},{size}
                        2. /findAll
                        3. /delete {id},{size}""");
            }
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void sendMessageScheduled() {
        StringBuilder stringBuilder = new StringBuilder();

        productRepository.findAll().forEach(product -> {
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
                    productRepository.deleteById(product.getProductId());
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
