package com.project.musinsastocknotificationbot.infrastructure.crawling.dto;

import com.project.musinsastocknotificationbot.domain.product.entity.Product;

public record TrackProductRequest (
    Long id,
    String size
) {

  public TrackProductRequest(Product product) {
    this(product.getProductId().getId(), product.getProductId().getSize());
  }
}
