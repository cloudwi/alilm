package com.project.musinsastocknotificationbot.crawling;

import com.project.musinsastocknotificationbot.crawling.dto.TrackProductRequest;
import com.project.musinsastocknotificationbot.crawling.dto.TrackProductResponse;
import com.project.musinsastocknotificationbot.product.dto.Response.CrawlingResponse;
import java.util.List;

public interface Crawling {

  public CrawlingResponse crawling (String url);

  List<TrackProductResponse> track(List<TrackProductRequest> trackProductRequests);
}
