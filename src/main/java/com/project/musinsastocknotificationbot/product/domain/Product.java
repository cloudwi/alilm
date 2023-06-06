package com.project.musinsastocknotificationbot.product.domain;

import com.project.musinsastocknotificationbot.common.entity.BaseTimeEntity;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;
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

    protected Product() {

    }

    public Product(ProductInfo productInfo, String title, String imageUrl) {
        this.productInfo = productInfo;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public ProductInfo getProductId() {
        return productInfo;
    }

    public String getMessage() {
        return String.format("""
            %s
            Title : %s
            """, productInfo.getMessage(), this.title);
    }
}
