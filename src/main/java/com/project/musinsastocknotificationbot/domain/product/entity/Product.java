package com.project.musinsastocknotificationbot.domain.product.entity;

import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class Product extends BaseTimeEntity implements Serializable {

    @EmbeddedId
    private ProductId productId;

    @Column(nullable = false)
    private String title;

    public Product() {

    }

    public Product(ProductId productId, String title) {
        this.productId = productId;
        this.title = title;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }
}
