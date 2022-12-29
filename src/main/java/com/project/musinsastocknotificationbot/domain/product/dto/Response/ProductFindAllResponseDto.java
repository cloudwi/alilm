package com.project.musinsastocknotificationbot.domain.product.dto.Response;

public class ProductFindAllResponseDto {
    private long id;
    private String size;

    public ProductFindAllResponseDto(long id, String size) {
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
