package com.project.musinsastocknotificationbot.product.service.impl;

import com.project.musinsastocknotificationbot.crawling.Crawling;
import com.project.musinsastocknotificationbot.crawling.dto.TrackProductRequest;
import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.repository.ProductRepository;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
import com.project.musinsastocknotificationbot.product.dto.Response.CrawlingResponse;
import com.project.musinsastocknotificationbot.product.event.ProductEvent;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MusinsaProductServiceImpl implements ProductService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ProductRepository productRepository;
    private final Crawling crawling;

    public MusinsaProductServiceImpl(ApplicationEventPublisher applicationEventPublisher,
        ProductRepository productRepository, Crawling crawling) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.productRepository = productRepository;
        this.crawling = crawling;
    }

    @Transactional
    public void save(ProductInfo productInfo) {
        String musinsaProductUrl = productInfo.getMusinsaProductUrl();

        CrawlingResponse crawlingResponse = crawling.crawling(musinsaProductUrl);
        String title = crawlingResponse.title();
        String imageUrl = crawlingResponse.imageUrl();

        Product product = Product.of(productInfo, title, imageUrl);

        String message = "등록 : " + title;
        applicationEventPublisher.publishEvent(ProductEvent.of(message));

        productRepository.save(product);
    }

    public void findAll() {
        List<Product> products = getProducts();

        StringBuilder stringBuilder = new StringBuilder();

        products.forEach(product -> {
            stringBuilder.append("Id : ");
            stringBuilder.append(product.getProductId().getId());
            stringBuilder.append(" Size : ");
            stringBuilder.append(product.getProductId().getSize());
            stringBuilder.append(" Title : ");
            stringBuilder.append(product.getTitle());
            stringBuilder.append("\n");
        });

        applicationEventPublisher.publishEvent(ProductEvent.of(stringBuilder.toString()));
    }

    @Scheduled(cron = "0/30 * * * * ?")
    @Transactional
    public void track() {
        StringBuilder stringBuilder = new StringBuilder();

        List<TrackProductRequest> trackProductRequests = getProducts()
            .stream()
            .map(TrackProductRequest::new)
            .toList();

        crawling.track(trackProductRequests).forEach(trackProductResponse -> {
            ProductInfo productInfo = trackProductResponse.toProductInfo();
            delete(productInfo);

            stringBuilder.append(trackProductResponse.getMessage());
        });

        applicationEventPublisher.publishEvent(ProductEvent.of(stringBuilder.toString()));
    }

    @Transactional
    public void delete(ProductInfo productInfo) {
        productRepository.deleteById(productInfo);
    }

    private List<Product> getProducts() {
        return productRepository.findAll();
    }
}
