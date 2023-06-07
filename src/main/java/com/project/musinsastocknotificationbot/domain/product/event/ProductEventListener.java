package com.project.musinsastocknotificationbot.domain.product.event;

import com.project.musinsastocknotificationbot.infrastructure.message.service.MessageService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

  private final MessageService messageService;

  public ProductEventListener(MessageService messageService) {
    this.messageService = messageService;
  }

  @Async
  @EventListener
  public void sendMessage(ProductEvent productEvent) {
    messageService.sendMessage(productEvent.message());
  }
}
