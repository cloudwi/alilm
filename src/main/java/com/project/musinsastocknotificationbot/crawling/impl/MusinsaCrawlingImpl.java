package com.project.musinsastocknotificationbot.crawling.impl;

import com.project.musinsastocknotificationbot.crawling.Crawling;
import com.project.musinsastocknotificationbot.crawling.dto.TrackProductRequest;
import com.project.musinsastocknotificationbot.crawling.dto.TrackProductResponse;
import com.project.musinsastocknotificationbot.product.dto.Response.CrawlingResponse;
import com.project.musinsastocknotificationbot.product.error.JsoupIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class MusinsaCrawlingImpl implements Crawling {

  private static final String BASE_URL = "https://www.musinsa.com/app/goods/";
  private static final String OPTION_ELEMENTS = "select.option1";
  private static final String PRODUCT_TITLE = "span.product_title";
  private static final String OPTION = "option";
  private static final String SOLDOUT = "품절";

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

  @Override
  public List<TrackProductResponse> track(List<TrackProductRequest> trackProductRequests) {
    List<TrackProductResponse> trackProductResponses = new ArrayList<>();

    trackProductRequests.forEach(trackProductRequest -> {
      long id = trackProductRequest.id();
      String size = trackProductRequest.size();
      String productUrl = BASE_URL + id;
      Document document = null;

      try {
        document = Jsoup.connect(productUrl).get();
      } catch (IOException e) {
        throw new JsoupIOException(e);
      }

      Elements elements = document.select(OPTION_ELEMENTS);
      String title = document.select(PRODUCT_TITLE).text();

      for (Element element : elements.select(OPTION)) {
        String target = element.text();

        if (target.contains(size) && !target.contains(SOLDOUT)) {
          trackProductResponses.add(TrackProductResponse.from(id, size, title, productUrl));
        }
      }
    });

    return trackProductResponses;
  }
}
