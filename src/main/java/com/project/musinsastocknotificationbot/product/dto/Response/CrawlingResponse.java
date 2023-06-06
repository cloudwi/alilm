package com.project.musinsastocknotificationbot.product.dto.Response;

import com.project.musinsastocknotificationbot.product.domain.Product;
import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;

public record CrawlingResponse(
    String title,
    String imageUrl
) {

  public static CrawlingResponse from(String title, String imageUrl) {
    return new CrawlingResponse(title, imageUrl);
  }

  public Product toProduct(ProductInfo productInfo) {
    return new Product(productInfo, this.title, this.imageUrl);
  }
}
