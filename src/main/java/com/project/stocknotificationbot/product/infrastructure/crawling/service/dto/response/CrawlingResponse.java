package com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.response;

public record CrawlingResponse(
    String title,
    String imageUrl
) {

    public static CrawlingResponse of(String title, String imageUrl) {
        return new CrawlingResponse(title, imageUrl);
    }
}
