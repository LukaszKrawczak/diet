package com.demo.diet.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class MyExceptionHandler {

  @ExceptionHandler(value = {HttpMessageNotReadableException.class})
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
    HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    MediaType contentType = headers.getContentType();
    if (contentType == null || !contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
      // jeśli Content-Type nie jest ustawiony na application/json
      // zwróć odpowiedź z błędem 415 Unsupported Media Type
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
        .body("Unsupported Media Type: " + contentType);
    }

    // w przeciwnym razie obsłuż wyjątek
    return ResponseEntity.status(status).body(ex.getMessage());
  }
}
