package com.project.musinsastocknotificationbot.domain.product.service.impl;

import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.domain.product.repository.ProductRepository;
import com.project.musinsastocknotificationbot.domain.product.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductTelegramServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private Document doc;

    public ProductTelegramServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.doc = null;
    }

    public ProductId save(long id, String size) {
        ProductId productId = new ProductId(id, size);
        String url = "https://www.musinsa.com/app/goods/" + id;

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        String title = doc.select("span.product_title").text();
        Product product = new Product(productId, title);
        return productRepository.save(product).getProductId();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public long delete(ProductId productId) {
        productRepository.deleteById(productId);
        return productId.getId();
    }

}
