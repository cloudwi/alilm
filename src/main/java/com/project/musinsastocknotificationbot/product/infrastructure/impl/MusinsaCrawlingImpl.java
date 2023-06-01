package com.project.musinsastocknotificationbot.product.infrastructure.impl;

import com.project.musinsastocknotificationbot.product.domain.dto.Response.CrawlingResponse;
import com.project.musinsastocknotificationbot.product.error.JsoupIOException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Repository;

@Repository
public class MusinsaCrawlingImpl {

  public CrawlingResponse crawling (String url) {
    try {
      String titleHTML = "span.product_title";
      String imageUrlLink = "div.product-img";

      Document document = Jsoup.connect(url).get();
      String title = document.select(titleHTML).text();
      String imageUrl = document.select(imageUrlLink).html();

      return CrawlingResponse.from(title, imageUrl);
    } catch (IOException e) {
      throw new JsoupIOException(e);
    }
  }
}
