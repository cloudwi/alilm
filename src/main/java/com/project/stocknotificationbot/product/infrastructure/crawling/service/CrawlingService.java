package com.project.stocknotificationbot.product.infrastructure.crawling.service;

import com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.request.CrawlingRequest;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.response.CrawlingResponse;

public interface CrawlingService {

    CrawlingResponse crawling(CrawlingRequest request);
}
