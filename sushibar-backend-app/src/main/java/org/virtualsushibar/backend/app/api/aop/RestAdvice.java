package org.virtualsushibar.backend.app.api.aop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.virtualsushibar.backend.app.api.dto.Message;
import org.virtualsushibar.backend.app.exception.SystemUnavailableException;

@RestControllerAdvice
@Slf4j
public class RestAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Message> genericError(Exception e) {
    log.error("Unexpected error", e);
    Message message = new Message("Unexpected error: \n" + e.toString());
    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(SystemUnavailableException.class)
  public ResponseEntity<Message> systemUnavailable(SystemUnavailableException e) {
    log.error("The system is currently unavailable", e);
    Message message = new Message("The system is currently unavailable");
    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
