package com.project.musinsastocknotificationbot.product.domain.idClass;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProductId implements Serializable {

    @Column
    private long id;
    @Column
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
