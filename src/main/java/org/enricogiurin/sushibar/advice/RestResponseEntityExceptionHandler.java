package org.enricogiurin.sushibar.advice;

import org.enricogiurin.sushibar.util.StringResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by enrico on 9/7/17.
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<StringResponse> handleConflict(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(StringResponse.of(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
