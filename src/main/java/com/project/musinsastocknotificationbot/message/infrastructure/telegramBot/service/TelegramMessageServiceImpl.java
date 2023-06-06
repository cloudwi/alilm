package com.project.musinsastocknotificationbot.message.infrastructure.telegramBot.service;

import com.project.musinsastocknotificationbot.message.infrastructure.telegramBot.domain.TelegramBotCommand;
import com.project.musinsastocknotificationbot.message.service.MessageService;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramMessageServiceImpl extends TelegramLongPollingBot implements MessageService {

    private final String telegramToken;
    private final ProductService productService;
    private final String chatId;

    public TelegramMessageServiceImpl(@Value("${secret.telegramToken}") String telegramToken, ProductService productService,
        @Value("${secret.chat_id}") String chatId) {
        this.telegramToken = telegramToken;
        this.productService = productService;
        this.chatId = chatId;
    }

    public void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "Musinsa_Stock_Notification_bot";
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

        ProductInfo productInfo = getProductInfo(inputMessage);

        switch (telegramBotCommand) {
            case ADD -> productService.save(productInfo);
            case FIND_ALL -> productService.findAll();
            case DELETE -> productService.delete(productInfo);
            case DEFAULT -> sendMessage(defaultMessage);
        }
    }

    private ProductInfo getProductInfo(String[] inputMessage) {
        String[] inputProductInfo = inputMessage[1].split(",");
        long productId = Long.parseLong(inputProductInfo[0]);
        String productSize = inputProductInfo[1];

        return ProductInfo.from(productId, productSize);
    }
}
