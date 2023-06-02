package com.project.musinsastocknotificationbot.telegramBot.service;

import com.project.musinsastocknotificationbot.product.domain.repository.ProductRepository;
import com.project.musinsastocknotificationbot.product.error.JsoupIOException;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import com.project.musinsastocknotificationbot.telegramBot.domain.TelegramBotCommand;
import com.project.musinsastocknotificationbot.telegramBot.infrastructure.TelegramWebClient;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class TelegramService extends TelegramLongPollingBot {

    private final String telegramToken;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final WebClient webClient;
    private final String chatId;
    private Document doc = null;

    private static final String BASE_URL = "https://www.musinsa.com/app/goods/";

    public TelegramService(@Value("${secret.telegramToken}") String telegramToken,
        ProductService productService, ProductRepository productRepository, TelegramWebClient telegramWebClient,
        @Value("${secret.chat_id}") String chatId) {

        this.telegramToken = telegramToken;
        this.productService = productService;
        this.productRepository = productRepository;
        this.webClient = telegramWebClient.getWebClient();
        this.chatId = chatId;
    }

    public static void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
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
        TelegramBotCommand telegramBotCommand = TelegramBotCommand.valueOfLInput(inputMessage[0]);
        String defaultMessage = """
                    올바른 명령어를 입력해주세요!
                    1. /add {id},{size}
                    2. /findAll
                    3. /delete {id},{size}""";

        switch (telegramBotCommand) {
            case ADD -> productService.save(inputMessage);
            case FIND_ALL -> productService.findAll();
            case DELETE -> productService.delete(inputMessage);
            default -> sendMessage(defaultMessage);
        }
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
                    stringBuilder.append(" 구매가능 ");
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
