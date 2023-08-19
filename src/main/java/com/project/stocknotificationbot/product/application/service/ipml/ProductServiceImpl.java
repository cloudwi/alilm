package com.project.stocknotificationbot.product.application.service.ipml;

import com.project.stocknotificationbot.product.application.service.ProductService;
import com.project.stocknotificationbot.product.domain.entity.Product;
import com.project.stocknotificationbot.product.domain.repository.ProductRepository;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.CrawlingService;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.request.CrawlingRequest;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.response.CrawlingResponse;
import com.project.stocknotificationbot.product.presentation.dto.request.CreateProductRequest;
import com.project.stocknotificationbot.product.presentation.dto.response.CreateProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CrawlingService crawlingService;

    public ProductServiceImpl(ProductRepository productRepository, CrawlingService crawlingService) {
        this.productRepository = productRepository;
        this.crawlingService = crawlingService;
    }

    @Override
    public CreateProductResponse create(CreateProductRequest request) {
        CrawlingResponse crawlingResponse = getCrawlingResponse(request);
        Product product = getProduct(request, crawlingResponse);
        Product savedProduct = productRepository.save(product);

        return CreateProductResponse.from(savedProduct);
    }

    private static Product getProduct(CreateProductRequest request, CrawlingResponse crawlingResponse) {
        String title = crawlingResponse.title();
        String imageUrl = crawlingResponse.imageUrl();
        return request.toProduct(title, imageUrl);

    }

    private CrawlingResponse getCrawlingResponse(CreateProductRequest request) {
        String realUrl = request.realId().toString();
        CrawlingRequest crawlingRequest = CrawlingRequest.of(realUrl);
        return crawlingService.crawling(crawlingRequest);
    }
}
