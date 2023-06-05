package com.project.musinsastocknotificationbot.product.domain;

import com.project.musinsastocknotificationbot.crawling.dto.TrackProductRequest;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
import com.project.musinsastocknotificationbot.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.io.Serializable;

@Entity
public class Product extends BaseTimeEntity implements Serializable {

    @EmbeddedId
    private ProductInfo productInfo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String imageUrl;

    public Product() {

    }

    public static Product of(ProductInfo productInfo, String title, String imageUrl) {
        return new Product(productInfo, title, imageUrl);
    }

    private Product(ProductInfo productInfo, String title, String imageUrl) {
        this.productInfo = productInfo;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ProductInfo getProductId() {
        return productInfo;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
