package com.project.musinsastocknotificationbot.global.error.entity;

import java.util.List;
import org.springframework.validation.BindingResult;

public record ErrorResponse(
    String message,
    List<CustomFieldError> customFieldErrors
) {
  public ErrorResponse(List<CustomFieldError> customFieldErrors) {
    this(null, customFieldErrors);
  }

  public static ErrorResponse of(BindingResult bindingResult) {
    return new ErrorResponse(CustomFieldError.of(bindingResult));
  }
}
