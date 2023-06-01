package com.project.musinsastocknotificationbot.common.error;

import com.project.musinsastocknotificationbot.common.error.entity.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.info("MethodArgumentNotValidException 발생");

    ErrorResponse errorResponse = ErrorResponse.of(e.getBindingResult());
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
