package com.hotel.microservice.common.web;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jakarta.validation.ConstraintViolationException;

//@RestControllerAdvice
@ControllerAdvice(basePackages = "com.hotel.microservice")
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    Map<String, String> fields = ex.getBindingResult().getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            fe -> fe.getField(),
            fe -> Optional.ofNullable(fe.getDefaultMessage()).orElse("Inválido"),
            (a,b) -> a, LinkedHashMap::new));

    Map<String,Object> body = new LinkedHashMap<>();
    body.put("status", 400);
    body.put("error", "Bad Request");
    body.put("message", "Errores de validación");
    body.put("fields", fields);
    return ResponseEntity.badRequest().body(body);
  }

  // Bean Validation en @PathVariable / @RequestParam
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String,Object>> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String,Object> body = new LinkedHashMap<>();
    body.put("status", 400);
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  // JSON mal formado o tipo incorrecto (ej: mandar "" donde espera Integer)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String,Object>> handleUnreadable(HttpMessageNotReadableException ex) {
    Map<String,Object> body = new LinkedHashMap<>();
    body.put("status", 400);
    body.put("error", "Bad Request");

    Throwable cause = ex.getMostSpecificCause();
    if (cause instanceof MismatchedInputException mie) {
      // nombre del campo y tipo esperado
      String path = mie.getPath().stream()
          .map(JsonMappingException.Reference::getFieldName)
          .filter(Objects::nonNull)
          .collect(Collectors.joining("."));
      body.put("message", "Tipo de dato inválido");
      body.put("field", path);
      body.put("expected", mie.getTargetType() != null ? mie.getTargetType().getSimpleName() : "desconocido");
    } else {
      body.put("message", "JSON mal formado o incompatible");
    }
    return ResponseEntity.badRequest().body(body);
  }

  // Fallback
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,Object>> handleGeneric(Exception ex) {
    Map<String,Object> body = new LinkedHashMap<>();
    body.put("status", 500);
    body.put("error", "Internal Server Error");
    body.put("message", "Error inesperado");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}