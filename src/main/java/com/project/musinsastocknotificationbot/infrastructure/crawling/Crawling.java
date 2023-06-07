package com.project.musinsastocknotificationbot.infrastructure.crawling;

import com.project.musinsastocknotificationbot.infrastructure.crawling.dto.TrackProductRequest;
import com.project.musinsastocknotificationbot.infrastructure.crawling.dto.TrackProductResponse;
import com.project.musinsastocknotificationbot.domain.product.dto.Response.CrawlingResponse;
import java.util.List;

public interface Crawling {

  CrawlingResponse crawling (String url);

  List<TrackProductResponse> track(List<TrackProductRequest> trackProductRequests);
}
