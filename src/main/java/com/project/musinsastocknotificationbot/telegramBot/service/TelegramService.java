package com.project.musinsastocknotificationbot.telegramBot.service;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
import com.project.musinsastocknotificationbot.product.domain.repository.ProductRepository;
import com.project.musinsastocknotificationbot.product.error.JsoupIOException;
import com.project.musinsastocknotificationbot.telegramBot.domain.TelegramBotCommand;
import com.project.musinsastocknotificationbot.telegramBot.infrastructure.TelegramWebClient;
import com.project.musinsastocknotificationbot.telegramBot.error.CustomTelegramApiException;
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
    private final ProductRepository productRepository;
    private final WebClient webClient;
    private final String chatId;
    private Document doc = null;

    private static final String BASE_URL = "https://www.musinsa.com/app/goods/";

    public TelegramService(@Value("${secret.telegramToken}") String telegramToken,
        ProductRepository productRepository, TelegramWebClient telegramWebClient,
        @Value("${secret.chat_id}") String chatId) {

        this.telegramToken = telegramToken;
        this.productRepository = productRepository;
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
        String[] inputMessage = update.getMessage().getText().split(" ");
        TelegramBotCommand telegramBotCommand = TelegramBotCommand.valueOf(inputMessage[0]);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        switch (telegramBotCommand) {
            case ADD -> {
                ProductInfo productInfo = getProductInfo(inputMessage);
                long productId = productInfo.getId();

                String musinsaProductUrl = BASE_URL + productId;

                try {
                    doc = Jsoup.connect(musinsaProductUrl).get();
                } catch (IOException e) {
                    throw new JsoupIOException(e);
                }

                String title = doc.select("span.product_title").text();
                String imageUrl = doc.select("div.product-img").html();

                Product product = Product.of(productInfo, title, imageUrl);
                productRepository.save(product);
            }
            case FIND_ALL -> {
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
            case DELETE -> {
                ProductInfo productInfo = getProductInfo(inputMessage);

                productRepository.deleteById(productInfo);
            }
            default -> sendMessage.setText("""
                    올바른 명령어를 입력해주세요!
                    1. /add {id},{size}
                    2. /findAll
                    3. /delete {id},{size}""");
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new CustomTelegramApiException(e);
        }
    }

    private static ProductInfo getProductInfo(String[] inputMessage) {
        String[] inputProductInfo = inputMessage[1].split(",");
        long productId = Long.parseLong(inputProductInfo[0]);
        String productSize = inputProductInfo[1];

        return ProductInfo.from(productId, productSize);
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void sendMessageScheduled() {
        StringBuilder stringBuilder = new StringBuilder();

        productRepository.findAll().forEach(product -> {
            String crawlingUrl = BASE_URL + product.getProductId().getId();

            try {
                doc = Jsoup.connect(crawlingUrl).get();
            } catch (IOException e) {
                throw new JsoupIOException(e);
            }

            Elements elements = doc.select("select.option1");
            String title = doc.select("span.product_title").text();
            for (Element element : elements.select("option")) {
                String target = element.text();
                if (target.contains(product.getProductId().getSize()) && !target.contains("(품절)")) {
                    stringBuilder.append(title);
                    stringBuilder.append(" : ");
                    stringBuilder.append("구매가능");
                    stringBuilder.append("\n");
                    stringBuilder.append("구매 링크 : ");
                    stringBuilder.append(crawlingUrl);
                    productRepository.deleteById(product.getProductId());
                }
            }
        });

        if (!stringBuilder.isEmpty()) {
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
