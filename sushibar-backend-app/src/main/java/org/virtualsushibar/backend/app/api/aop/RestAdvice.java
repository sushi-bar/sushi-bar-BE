package org.virtualsushibar.backend.app.api.aop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.virtualsushibar.backend.app.api.dto.Message;

@RestControllerAdvice
@Slf4j
public class RestAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> genericError(Exception e) {
        log.error("Unexpected error", e);
        Message message = Message.builder()
                .mgs("Unexpected error: \n" + e.toString())
                .build();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
