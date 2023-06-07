package com.project.musinsastocknotificationbot.common.error.entity;

import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public record CustomFieldError (
    String field,
    String value,
    String reason
) {

  public static List<CustomFieldError> from(BindingResult bindingResult) {
    return bindingResult.getFieldErrors()
        .stream()
        .map(CustomFieldError::from)
        .toList();
  }

  private static CustomFieldError from(FieldError fieldError) {
    String field = fieldError.getField();
    String value = (String) fieldError.getRejectedValue();
    String reason = fieldError.getDefaultMessage();

    return new CustomFieldError(field, value, reason);
  }
}
