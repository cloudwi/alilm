package com.project.musinsastocknotificationbot.product.service.impl;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
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
    private static final String BASE_URL = "https://www.musinsa.com/app/goods/";

    private static final String TITLE_HTML = "span.product_title";
    private static final String IMAGE_URL_LINK = "div.product-img";

    public ProductTelegramServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductInfo save(long productId, String productSize) {
        Document doc;
        ProductInfo productInfo = ProductInfo.from(productId, productSize);
        String url = BASE_URL + productId;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new JsoupIOException(e);
        }

        String title = doc.select(TITLE_HTML).text();
        String imageUrl = doc.select(IMAGE_URL_LINK).html();

        Product product = Product.of(productInfo, title, imageUrl);

        return productRepository.save(product).getProductId();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public long delete(ProductInfo productInfo) {
        productRepository.deleteById(productInfo);

        return productInfo.getId();
    }
}
