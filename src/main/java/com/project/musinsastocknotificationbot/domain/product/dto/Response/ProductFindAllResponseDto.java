package com.project.musinsastocknotificationbot.domain.product.dto.Response;

public class ProductFindAllResponseDto {
    private long id;
    private String size;
    private String title;
    private String imageHTML;

    public ProductFindAllResponseDto() {
    }

    public ProductFindAllResponseDto(long id, String size, String title, String imageHTML) {
        this.id = id;
        this.size = size;
        this.title = title;
        this.imageHTML = imageHTML;
    }

    public long getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public String getImageHTML() {
        return imageHTML;
    }
}
