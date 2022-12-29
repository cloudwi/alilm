package com.project.musinsastocknotificationbot.domain.product.entity.idClass;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProductId implements Serializable {

    private long id;
    private String size;

    public ProductId() {
    }

    public ProductId(long id, String size) {
        this.id = id;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public String getSize() {
        return size;
    }
}
