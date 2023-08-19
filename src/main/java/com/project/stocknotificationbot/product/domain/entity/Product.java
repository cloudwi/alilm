package com.project.stocknotificationbot.product.domain.entity;

import com.project.stocknotificationbot.common.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long realId;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String imageUrl;

    protected Product() {}

    public Product(Long realId, String size, String title, String imageUrl) {
        this.realId = realId;
        this.size = size;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getRealId() {
        return realId;
    }

    public String getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
