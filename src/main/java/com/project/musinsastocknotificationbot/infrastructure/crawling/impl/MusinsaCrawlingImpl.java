package com.project.musinsastocknotificationbot.infrastructure.crawling.impl;

import com.project.musinsastocknotificationbot.infrastructure.crawling.Crawling;
import com.project.musinsastocknotificationbot.infrastructure.crawling.dto.TrackProductRequest;
import com.project.musinsastocknotificationbot.infrastructure.crawling.dto.TrackProductResponse;
import com.project.musinsastocknotificationbot.domain.product.dto.Response.CrawlingResponse;
import com.project.musinsastocknotificationbot.domain.product.error.JsoupIOException;
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
  private static final String TITLE_HTML = "span.product_title";
  private static final String IMAGE_URL_LINK = "div.product-img";

  public CrawlingResponse crawling (String url) {
    try {
      Document document = Jsoup.connect(url).get();
      String title = document.select(TITLE_HTML).text();
      String imageUrl = document.select(IMAGE_URL_LINK).html();

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
