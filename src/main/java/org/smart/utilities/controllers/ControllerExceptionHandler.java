package org.smart.utilities.controllers;

import org.smart.utilities.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthenticationCredentialsNotFoundException(
      AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You have to login");
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}


