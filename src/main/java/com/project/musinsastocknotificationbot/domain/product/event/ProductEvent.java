package com.project.musinsastocknotificationbot.domain.product.event;

public record ProductEvent(
    String message
) {

  public static ProductEvent of(String message) {
    return new ProductEvent(message);
  }
}

