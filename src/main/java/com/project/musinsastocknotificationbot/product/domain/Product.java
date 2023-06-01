package com.project.musinsastocknotificationbot.product.domain;

import com.project.musinsastocknotificationbot.product.domain.idClass.ProductId;
import com.project.musinsastocknotificationbot.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.io.Serializable;

@Entity
public class Product extends BaseTimeEntity implements Serializable {

    @EmbeddedId
    private ProductId productId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Lob
    private String imageUrl;

    public Product() {

    }

    public Product(ProductId productId, String title, String imageUrl) {
        this.productId = productId;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
