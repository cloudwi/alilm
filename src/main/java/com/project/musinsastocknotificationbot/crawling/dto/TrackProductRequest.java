package com.project.musinsastocknotificationbot.crawling.dto;

import com.project.musinsastocknotificationbot.product.domain.Product;

public record TrackProductRequest (
    Long id,
    String size
) {

  public TrackProductRequest(Product product) {
    this(product.getProductId().getId(), product.getProductId().getSize());
  }
}
