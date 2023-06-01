package com.project.musinsastocknotificationbot.product.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProductInfo implements Serializable {

    @Column
    private long id;
    @Column
    private String size;

    protected ProductInfo() {
    }

    private ProductInfo(long id, String size) {
        this.id = id;
        this.size = size;
    }

    public static ProductInfo from(long productId, String productSize) {
        return new ProductInfo(productId, productSize);
    }

    public long getId() {
        return id;
    }

    public String getSize() {
        return size;
    }
}
