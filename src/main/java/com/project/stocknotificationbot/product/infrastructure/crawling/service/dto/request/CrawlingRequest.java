package com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.request;

public record CrawlingRequest(
    Long productRealId
) {

    public static CrawlingRequest of(String realUrl) {
        return new CrawlingRequest(Long.parseLong(realUrl));
    }
}
