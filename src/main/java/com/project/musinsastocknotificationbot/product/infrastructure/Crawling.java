package com.project.musinsastocknotificationbot.product.infrastructure;

import com.project.musinsastocknotificationbot.product.domain.dto.Response.CrawlingResponse;

public interface Crawling {

  public CrawlingResponse crawling (String url);
}
