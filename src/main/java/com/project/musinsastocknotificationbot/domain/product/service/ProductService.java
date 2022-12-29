package com.project.musinsastocknotificationbot.domain.product.service;

import com.project.musinsastocknotificationbot.domain.product.dto.request.ProductSaveRequestDto;
import com.project.musinsastocknotificationbot.domain.product.entity.Product;
import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.domain.product.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private Document doc;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.doc = null;
    }

    public ProductId save(ProductSaveRequestDto productSaveRequestDto) {
        ProductId productId = new ProductId(productSaveRequestDto.getId(), productSaveRequestDto.getSize());
        String url = "https://www.musinsa.com/app/goods/" + productSaveRequestDto.getId();

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
