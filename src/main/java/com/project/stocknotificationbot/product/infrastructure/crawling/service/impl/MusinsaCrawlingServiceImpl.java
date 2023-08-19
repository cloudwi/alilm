package com.project.stocknotificationbot.product.infrastructure.crawling.service.impl;

import com.project.stocknotificationbot.product.infrastructure.crawling.error.JsoupIOException;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.CrawlingService;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.request.CrawlingRequest;
import com.project.stocknotificationbot.product.infrastructure.crawling.service.dto.response.CrawlingResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class MusinsaCrawlingServiceImpl implements CrawlingService {

  private static final String BASE_URL = "https://www.musinsa.com/app/goods/";
  private static final String OPTION_ELEMENTS = "select.option1";
  private static final String PRODUCT_TITLE = "span.product_title";
  private static final String OPTION = "option";
  private static final String SOLD_OUT = "품절";
  private static final String TITLE_HTML = "span.product_title";
  private static final String IMAGE_URL_LINK = "div.product-img";

  private static final Logger logger = getLogger(MusinsaCrawlingServiceImpl.class);

//  @Override
//  public List<TrackProductResponse> track(List<TrackProductRequest> trackProductRequests) {
//    List<TrackProductResponse> trackProductResponses = new ArrayList<>();
//
//    trackProductRequests.forEach(trackProductRequest -> {
//      long id = trackProductRequest.id();
//      String size = trackProductRequest.size();
//      String productUrl = BASE_URL + id;
//      Document document = null;
//
//      try {
//        document = Jsoup.connect(productUrl).get();
//      } catch (IOException e) {
//        throw new JsoupIOException(e);
//      }
//
//      Elements elements = document.select(OPTION_ELEMENTS);
//      String title = document.select(PRODUCT_TITLE).text();
//
//      for (Element element : elements.select(OPTION)) {
//        String target = element.text();
//
//        if (target.contains(size) && !target.contains(SOLD_OUT)) {
//          trackProductResponses.add(TrackProductResponse.from(id, size, title, productUrl));
//        }
//      }
//    });
//
//    return trackProductResponses;
//  }

  @Override
  public CrawlingResponse crawling(CrawlingRequest request) {
      Document document = getDocument(request);
      Elements titleElements = document.select(TITLE_HTML);
      Elements imageUrlElements = document.select(IMAGE_URL_LINK);

      extracted(titleElements, "크롤링 대상 웹사이트에서 title 데이터를 가져오지 못했습니다. url : {}", request, "크롤링 대상 웹사이트에서 title 데이터를 가져오지 못했습니다.");
      extracted(imageUrlElements, "크롤링 대상 웹사이트에서 imageUrl 데이터를 가져오지 못했습니다. url : {}", request, "크롤링 대상 웹사이트에서 imageUrl 데이터를 가져오지 못했습니다.");

      String title = titleElements.text();
      String imageUrl = imageUrlElements.html();

      return CrawlingResponse.of(title, imageUrl);
  }

  private static Document getDocument(CrawlingRequest request) {
    try {
        return Jsoup.connect(BASE_URL + request.productRealId()).get();
    } catch (IOException e) {
      logger.error("클롤링 도중에 예외가 발생했어요. url : {}", BASE_URL + request.productRealId());
      throw new JsoupIOException("크롤링 도중에 예외가 발생했어요.");
    }
  }

  private static void extracted(Elements elements, String format, CrawlingRequest request, String message) {
    if (elements.isEmpty()) {
      logger.error(format, BASE_URL + request.productRealId());
      throw new JsoupIOException(message);
    }
  }
}
