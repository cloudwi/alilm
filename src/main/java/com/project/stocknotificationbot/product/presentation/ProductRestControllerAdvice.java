package com.project.stocknotificationbot.product.presentation;

import com.project.stocknotificationbot.product.infrastructure.crawling.error.JsoupIOException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductRestControllerAdvice {

    @ExceptionHandler(JsoupIOException.class)
    public String handleJsoupIOException(JsoupIOException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
