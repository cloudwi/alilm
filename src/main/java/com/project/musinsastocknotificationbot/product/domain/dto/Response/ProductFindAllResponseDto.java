package com.project.musinsastocknotificationbot.product.domain.dto.Response;

import com.project.musinsastocknotificationbot.product.domain.Product;

public record ProductFindAllResponseDto (
    long id,
    String size,
    String title,
    String imageHTML
) {

  public static ProductFindAllResponseDto from(Product product) {
    return new ProductFindAllResponseDto(product.getProductId().getId(),
        product.getProductId().getSize(), product.getTitle(), product.getImageUrl());
  }
}
