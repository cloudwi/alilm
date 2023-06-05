package com.project.musinsastocknotificationbot.crawling.dto;

import com.project.musinsastocknotificationbot.product.domain.vo.ProductInfo;

public record TrackProductResponse (
  long id,
  String size,
  String title,
  String url
) {

  public static TrackProductResponse from(long id, String size, String title, String url) {
    return new TrackProductResponse(id, size, title, url);
  }

  public ProductInfo toProductInfo() {
    return ProductInfo.from(this.id, this.size);
  }

  public String getMessage() {
    StringBuffer stringBuffer = new StringBuffer();

    stringBuffer.append("구매가능");
    stringBuffer.append("\n");
    stringBuffer.append("상품명 : ");
    stringBuffer.append(title);
    stringBuffer.append("\n");
    stringBuffer.append("사이즈 : ");
    stringBuffer.append(size);
    stringBuffer.append("\n");
    stringBuffer.append("구매링크 : ");
    stringBuffer.append(url);
    stringBuffer.append("\n");

    return stringBuffer.toString();
  }
}
