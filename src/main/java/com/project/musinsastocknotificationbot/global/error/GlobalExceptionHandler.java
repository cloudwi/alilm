package com.project.musinsastocknotificationbot.global.error;

import com.project.musinsastocknotificationbot.global.error.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

    ErrorResponse errorResponse = ErrorResponse.of(e.getBindingResult());
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
