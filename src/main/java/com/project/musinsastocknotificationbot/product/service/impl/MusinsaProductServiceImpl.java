package com.project.musinsastocknotificationbot.product.service.impl;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.dto.Response.CrawlingResponse;
import com.project.musinsastocknotificationbot.product.domain.repository.ProductRepository;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
import com.project.musinsastocknotificationbot.product.infrastructure.Crawling;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import com.project.musinsastocknotificationbot.telegramBot.service.TelegramService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Transactional(readOnly = true)
public class MusinsaProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Crawling crawling;

    public MusinsaProductServiceImpl(ProductRepository productRepository,
        Crawling crawling) {
        this.productRepository = productRepository;
        this.crawling = crawling;
    }

    @Transactional
    public void save(String[] inputMessage) {
        ProductInfo productInfo = getProductInfo(inputMessage);
        String musinsaProductUrl = productInfo.getMusinsaProductUrl();

        CrawlingResponse crawlingResponse = crawling.crawling(musinsaProductUrl);
        String title = crawlingResponse.title();
        String imageUrl = crawlingResponse.imageUrl();

        Product product = Product.of(productInfo, title, imageUrl);

        TelegramService.sendMessage("등록 : " + title);

        productRepository.save(product);
    }

    public void findAll() {
        List<Product> products = productRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        SendMessage sendMessage = new SendMessage();

        products.forEach(product -> {
            stringBuilder.append("Id : ");
            stringBuilder.append(product.getProductId().getId());
            stringBuilder.append(" Size : ");
            stringBuilder.append(product.getProductId().getSize());
            stringBuilder.append(" Title : ");
            stringBuilder.append(product.getTitle());
            stringBuilder.append("\n");
        });

        TelegramService.sendMessage(sendMessage.toString());
    }

    @Transactional
    public void delete(String[] inputMessage) {
        ProductInfo productInfo = getProductInfo(inputMessage);

        productRepository.deleteById(productInfo);
    }

    private ProductInfo getProductInfo(String[] inputMessage) {
        String[] inputProductInfo = inputMessage[1].split(",");
        long productId = Long.parseLong(inputProductInfo[0]);
        String productSize = inputProductInfo[1];

        return ProductInfo.from(productId, productSize);
    }
}
