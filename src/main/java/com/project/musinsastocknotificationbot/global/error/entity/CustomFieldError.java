package com.project.musinsastocknotificationbot.global.error.entity;

import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public record CustomFieldError (
    String field,
    String value,
    String reason
) {

  public static List<CustomFieldError> of(BindingResult bindingResult) {
    return bindingResult.getFieldErrors()
        .stream()
        .map(CustomFieldError::of)
        .toList();
  }

  private static CustomFieldError of(FieldError fieldError) {
    String field = fieldError.getField();
    String value = (String) fieldError.getRejectedValue();
    String reason = fieldError.getDefaultMessage();

    return new CustomFieldError(field, value, reason);
  }
}
