package com.project.musinsastocknotificationbot.product.domain.dto.Response;

import com.project.musinsastocknotificationbot.product.domain.Product;

public record ProductFindAllResponse(
    long id,
    String size,
    String title,
    String imageHTML
) {

  public static ProductFindAllResponse from(Product product) {
    return new ProductFindAllResponse(product.getProductId().getId(),
        product.getProductId().getSize(), product.getTitle(), product.getImageUrl());
  }
}
