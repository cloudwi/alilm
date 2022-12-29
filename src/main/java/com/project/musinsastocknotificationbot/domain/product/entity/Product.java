package com.project.musinsastocknotificationbot.domain.product.entity;

import com.project.musinsastocknotificationbot.domain.product.entity.idClass.ProductId;
import com.project.musinsastocknotificationbot.global.entity.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class Product extends BaseTimeEntity implements Serializable {

    @EmbeddedId
    private ProductId productId;

    public Product() {

    }

    public Product(ProductId productId) {
        this.productId = productId;
    }

    public ProductId getProductId() {
        return productId;
    }
}
