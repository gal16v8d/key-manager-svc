package com.gsdd.keymanager.controllers.advice;

import com.gsdd.keymanager.exceptions.MissingHeaderException;
import com.gsdd.keymanager.exceptions.PasswordException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class KmgrControllerAdvice {

  @ExceptionHandler({MissingHeaderException.class, PasswordException.class})
  public ResponseEntity<Object> badPasswordHandler(Exception e, WebRequest request) {
    log.warn("Error while consuming endpoint {}", request, e);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("detail", e.getMessage()));
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> defaultHandler(Exception e, WebRequest request) {
    log.warn("Error while consuming endpoint {}", request, e);
    return ResponseEntity.internalServerError().body(Map.of("detail", e.getMessage()));
  }

}
