package com.project.musinsastocknotificationbot.product.service.impl;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.idClass.ProductId;
import com.project.musinsastocknotificationbot.product.domain.repository.ProductRepository;
import com.project.musinsastocknotificationbot.product.error.JsoupIOException;
import com.project.musinsastocknotificationbot.product.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductTelegramServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final String BASE_UTL = "https://www.musinsa.com/app/goods/";

    public ProductTelegramServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductId save(long id, String size) {
        Document doc;
        ProductId productId = new ProductId(id, size);
        String url = BASE_UTL + id;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new JsoupIOException(e);
        }

        String title = doc.select("span.product_title").text();
        String imageUrl = doc.select("div.product-img").html();

        Product product = new Product(productId, title, imageUrl);

        return productRepository.save(product).getProductId();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public long delete(ProductId productId) {
        productRepository.deleteById(productId);
        return productId.getId();
    }
}
